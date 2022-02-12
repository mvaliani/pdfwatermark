package com.mvaliani.pdfwatermark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
/*
* 
* PDF Watermark 
* adds / removes watermarks as layer into pdf  
* It is useful for workflows where you have to add some content to a pdf that is already "closed" and cannot be not recreated
* (Examples: Graphometric signatures)
* 
*/
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) throws IOException, DocumentException {
    SpringApplication.run(DemoApplication.class, args);
    
    // adding Watermark 
    byte[] bytes = OCGWatermark.addPDFWatermark();
    Files.write(new File("c:/temp/dummyaddedWM.pdf").toPath(), bytes);
    // Adds watermark as layer (marking it as common name)
    String wmName = DemoConfigs.PDF_WM_LAYER_ID;
    // removes watermark from the pdf looking in the layers
    // it cleans up also the dictionary
    PdfReader reader = new PdfReader(Files.readAllBytes(Paths.get("C:/temp/dummyaddedWM.pdf")));
    OCGRemover ocgRemover = new OCGRemover();
    ocgRemover.removeLayers(reader, wmName);
  }

}
