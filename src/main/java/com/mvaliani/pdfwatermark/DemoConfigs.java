package com.mvaliani.pdfwatermark;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DemoConfigs {

  public static String PDF_FILE_IN;

  @Value("${pdf.file.in:dummy.pdf}")
  public void setPDFFileInStatic(String pdfFileIn) {
    DemoConfigs.PDF_FILE_IN = pdfFileIn;
  }

  public static String PDF_WM_LAYER_ID;

  @Value("${pdf.wmlayer.id:ADDEDWATERMARK}")
  public void setPDFWMLayerIdStatic(String pdfWMLayerId) {
    DemoConfigs.PDF_WM_LAYER_ID = pdfWMLayerId;
  }

  public static Boolean PDF_WM_LAYER_OVERCONTENT;

  @Value("${pdf.wmlayer.overcontent:true}")
  public void setPDFWMLayerOverContentStatic(Boolean pdfWMLayerOverContent) {
    DemoConfigs.PDF_WM_LAYER_OVERCONTENT = pdfWMLayerOverContent;
  }

  public static String PDF_WM_LAYER_FONTNAME;

  @Value("${pdf.wm.layer.fontname:ARIAL}")
  public void setPDFWMLayerFontNameStatic(String pdfWMLayerFontName) {
    DemoConfigs.PDF_WM_LAYER_FONTNAME = pdfWMLayerFontName;
  }

  public static Boolean PDF_WM_LAYER_FONTBOLD;

  @Value("${pdf.wm.layer.fontbold:true}")
  public void setPDFWMLayerFontBoldStatic(Boolean pdfWMLayerFontBold) {
    DemoConfigs.PDF_WM_LAYER_FONTBOLD = pdfWMLayerFontBold;
  }

  public static Integer PDF_WM_LAYER_FONTSIZE;

  @Value("${pdf.wm.layer.fontsize:15}")
  public void setPDFWMLayerFontSizeStatic(Integer pdfWMLayerFontSize) {
    DemoConfigs.PDF_WM_LAYER_FONTSIZE = pdfWMLayerFontSize;
  }

  public static String PDF_WM_LAYER_FONTCOLOR;

  @Value("${pdf.wm.layer.fontcolor:0xFF0000}")
  public void setPDFWMLayerFontColorStatic(String pdfWMLayerFontColor) {
    DemoConfigs.PDF_WM_LAYER_FONTCOLOR = pdfWMLayerFontColor;
  }

  public static float PDF_WM_LAYER_OPACITY;

  @Value("${pdf.wm.layer.opacity:1}")
  public void setPDFWMLayerOpacityStatic(float pdfWMLayerOpacity) {
    DemoConfigs.PDF_WM_LAYER_OPACITY = pdfWMLayerOpacity;
  }

  public static Integer PDF_WM_LAYER_LEADING;

  @Value("${pdf.wm.layer.leading:1F}")
  public void setPDFWMLayerLeadingStatic(Integer pdfWMLayerLeading) {
    DemoConfigs.PDF_WM_LAYER_LEADING = pdfWMLayerLeading;
  }

  public static Boolean PDF_WM_LAYER_CENTERED;

  @Value("${pdf.wm.layer.centered:true}")
  public void setPDFWMLayerCenteredStatic(Boolean pdfWMLayerCentered) {
    DemoConfigs.PDF_WM_LAYER_CENTERED = pdfWMLayerCentered;
  }

  public static Integer PDF_WM_LAYER_ROTATION;

  @Value("${pdf.wm.layer.rotation:0}")
  public void setPDFWMLayerRotationStatic(Integer pdfWMLayerRotation) {
    DemoConfigs.PDF_WM_LAYER_ROTATION = pdfWMLayerRotation;
  }

  public static String PDF_WM_LAYER_TEXT;

  @Value("${pdf.wm.layer.text}")
  public void setPDFWMLayerTextStatic(String pdfWMLayerText) {
    DemoConfigs.PDF_WM_LAYER_TEXT = pdfWMLayerText;
  }

  @Value("${pdf.fileoutaddwm:dummy.pdf}")
  private String pdfFileoutaddwm;

  @Value("${pdf.fileoutremovedwm:dummy.pdf}")
  private String pdfFileoutremovedwm;

  public String getPdfFileoutaddwm() {
    return pdfFileoutaddwm;
  }

  public void setPdfFileoutaddwm(String pdfFileoutaddwm) {
    this.pdfFileoutaddwm = pdfFileoutaddwm;
  }

  public String getPdfFileoutremovedwm() {
    return pdfFileoutremovedwm;
  }

  public void setPdfFileoutremovedwm(String pdfFileoutremovedwm) {
    this.pdfFileoutremovedwm = pdfFileoutremovedwm;
  }

}
