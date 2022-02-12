package com.mvaliani.pdfwatermark;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfLayer;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;

/*
 * $Id$
 *
 * Adapted for OCG add for pdf signature - eWard 
 *  Adds a watermark text as layer on the undercontent of the pdf 
 *  Style (font, color etc) of the text should be parametrized 
 *  
 * This file is part of the iText (R) project.
 * Copyright (c) 1998-2014 iText Group NV
 * Authors: Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
 * ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
 * OF THIRD PARTY RIGHTS
 */
@Component
public class OCGWatermark {

  public static byte[] addPDFWatermark() throws IOException {

    // PDF Input File (from properties)
    byte[] pdfFileIn = Files.readAllBytes(Paths.get(DemoConfigs.PDF_FILE_IN));

    // watermark Layer ID
    String pdfWMLayerId = DemoConfigs.PDF_WM_LAYER_ID;

    // Font settings
    String pdfWMLayerFontName = DemoConfigs.PDF_WM_LAYER_FONTNAME;
    boolean pdfWMLayerFontBold = DemoConfigs.PDF_WM_LAYER_FONTBOLD;
    int pdfWMLayerFontSize = DemoConfigs.PDF_WM_LAYER_FONTSIZE;
    String pdfWMLayerFontColor = DemoConfigs.PDF_WM_LAYER_FONTCOLOR;
    float pdfWMLayerOpacity = DemoConfigs.PDF_WM_LAYER_OPACITY;

    Boolean pdfWMLayerPrintOverContent = DemoConfigs.PDF_WM_LAYER_OVERCONTENT;

    // watermark position
    int pdfWMLayerRotation = DemoConfigs.PDF_WM_LAYER_ROTATION;
    // watermark Text
    String pdfWMText = DemoConfigs.PDF_WM_LAYER_TEXT;
    Date today = new Date();
    /*
     * waterMarkText += "Date " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(today) + " at " + new
     * java.text.SimpleDateFormat("HH:mm").format(today) + " by Dr. Philip Brown ";
     */

    int pdfWMLeading = DemoConfigs.PDF_WM_LAYER_LEADING;

    float xPos = 50;    
    float yPos = 400;    

    return printFooterWatermark(
        pdfFileIn,
        pdfWMLayerId,
        pdfWMText,
        pdfWMLayerPrintOverContent,
        pdfWMLayerFontName,
        pdfWMLayerFontBold,
        pdfWMLayerFontSize,
        pdfWMLayerFontColor,
        pdfWMLayerOpacity,
        xPos,        
        yPos,        
        pdfWMLayerRotation,
        pdfWMLeading);
  }

  /**
   * Stampa un Watermark sul documento PDF rappresentato dal byte array in input.
   * 
   * @param watermarkName
   *          nome del layer su cui applicare il watermark (serve per una possibile rimozione
   * @param originalPdf
   *          documento PDF originale come byte array
   * @param waterMarkText
   *          testo del watermark
   * @param printOverContent
   *          indica se stampare in sovraimpressione (true) o come filigrana (false)
   * @param fontSize
   *          dimensione del font (in punti)
   * @param opacity
   *          opacità del watermark (0-1 con 0 completamente trasparente)
   * @param centered
   *          (boolean) indica se la posizione del watermark è centrata rispetto alle pagine
   * @param x
   *          posizione orizzontale in punti (se centered=true è l'offset rispetto al centro)
   * @param y
   *          posizione verticale in punti (se centered=true è l'offset rispetto al centro)
   * @param rotation
   *          angolo di rotazione in gradi
   * @param fontName
   *          nome del font tra "HELVETICA", "COURIER" o "TIMES"
   * @param bold
   *          indica se il font deve essere in grassetto
   * @param fontColor
   *          colore del font come stringa di 6 cifre esadecimali
   * 
   **/
  public static byte[] printFooterWatermark(
      byte[] pdfWMFileIn,
      String pdfWMLayerId,
      String pdfWMLayerText,
      boolean pdfWMLayerPrintOverContent,
      String pdfWMLayerfontName,
      boolean pdfWMLayerFontBold,
      int pdfWMLayerfontSize,
      String pdfWMLayerFontColor,
      float pdfLayerOpacity,
      float xPos,      
      float yPos,      
      int pdfWMLayerRotation,
      float leading) {

    try {

      // Font Settings
      Font font = new Font();
      font.setSize(pdfWMLayerfontSize);
      font.setFamily(pdfWMLayerfontName);

      if (pdfWMLayerFontBold) {
        font.setStyle(Font.BOLD);
      }

      Color color = null;
      try {
        color = Color.decode(pdfWMLayerFontColor);
      } catch (Exception e) {
        color = Color.LIGHT_GRAY;
      }
      font.setColor(new BaseColor(color.getRGB()));

      // reads the file and gets the n pages
      PdfReader reader = new PdfReader(pdfWMFileIn);
      int n = reader.getNumberOfPages();

      // preparing output
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PdfStamper stamp = new PdfStamper(reader, outputStream);

      // creating the layer for visibility
      PdfLayer wmLayer = new PdfLayer(pdfWMLayerId, stamp.getWriter());
      wmLayer.setOnPanel(true);
      wmLayer.setPrint("print", true);
      wmLayer.setOn(true);
      wmLayer.setView(true);
      
      Integer i = 1;
      // adding watermark for eack page
      while (i <= n) {

        PdfContentByte pdc;
        if (pdfWMLayerPrintOverContent)
          pdc = stamp.getOverContent(i);
        else
          pdc = stamp.getUnderContent(i);

        PdfGState gs = new PdfGState();
        gs.setFillOpacity(pdfLayerOpacity);
        pdc.setGState(gs);
        pdc.beginLayer(wmLayer);
        
        // creating the text
        ColumnText ct = new ColumnText(pdc);
        Phrase phrase = new Phrase();
        phrase.setFont(font);
        phrase.add(pdfWMLayerText);

        
        /*
         * Note: for setSimpleColumn (the style has to be embedded into the phrase) pdc.setFontAndSize(bf, fontSize); pdc.setColorFill(new
         * BaseColor(color.getRGB())); pdc.showTextAligned(Element.ALIGN_LEFT, waterMarkText, xpos, ypos, rotation);
         */
        // Impostazione Font
        String baseFontName = BaseFont.HELVETICA;
        
        if (pdfWMLayerfontName.equalsIgnoreCase("HELVETICA")) {
          if (pdfWMLayerFontBold)
            baseFontName = BaseFont.HELVETICA_BOLD;
          else
            baseFontName = BaseFont.HELVETICA;
        } else if (pdfWMLayerfontName.equalsIgnoreCase("COURIER")) {
          if (pdfWMLayerFontBold)
            baseFontName = BaseFont.COURIER_BOLD;
          else
            baseFontName = BaseFont.COURIER;
        } else if (pdfWMLayerfontName.equalsIgnoreCase("TIMES")) {
          if (pdfWMLayerFontBold)
            baseFontName = BaseFont.TIMES_BOLD;
          else
            baseFontName = BaseFont.TIMES_ROMAN;
        }
        
        BaseFont bf = BaseFont.createFont(baseFontName, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        float ascent = bf.getAscentPoint(pdfWMLayerText, pdfWMLayerfontSize);
        float descent = bf.getDescentPoint(pdfWMLayerText, pdfWMLayerfontSize);
        float textWdith = bf.getWidthPoint(pdfWMLayerText, pdfWMLayerfontSize);

        // Recupero le informazioni riguardo l'altezza
        float fontHeight = ascent - descent;        
        float llx = xPos;
        float urx = xPos + textWdith;

        float lly = reader.getPageSize(i).getHeight() - yPos -fontHeight;
        float ury = lly + fontHeight;
        
        ColumnText.showTextAligned(pdc, Element.ALIGN_LEFT, phrase, xPos, yPos, pdfWMLayerRotation);
        
        /*
        BaseColor bColor = WebColors.getRGBColor("#A000FF");
        //BaseColor bColor = new BaseColor(0xFF, 0x23, 0x23);

        Rectangle rect = new Rectangle(llx, lly, urx, ury);
        rect.setRotation(90);
        rect.rotate();
        rect.setBorderColor(bColor);        
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(2);
        
        pdc.rectangle(rect);
        
        ct.setText(phrase);
        ct.setSimpleColumn(llx, lly, urx, ury);
        */
        Integer status = ct.go();
        System.out.println(status.toString());

        pdc.endLayer();
        i++;
      }

      stamp.close();

      byte[] result = outputStream.toByteArray();
      return result;
    } catch (Exception e) {
      return null;
    }
  }
}
