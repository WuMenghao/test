package com.brillilab.test;

import org.apache.poi.xslf.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiTest {

    @Test
    public void newPresentation() throws IOException {

        //XMLSlideShow
        XMLSlideShow ppt=new XMLSlideShow();

        //XSLFSlideMaster
        XSLFSlideMaster defaultMaster=ppt.getSlideMasters().get(0);

        //Title
        XSLFSlideLayout titleLayout=defaultMaster.getLayout(SlideLayout.TITLE);
        XSLFSlide titleSlide=ppt.createSlide(titleLayout);
        XSLFTextShape title1=titleSlide.getPlaceholder(0);
        title1.setText("Hello");

        //TitleBody
        XSLFSlideLayout bodyLayout=defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        XSLFSlide titleBodySlide=ppt.createSlide(bodyLayout);

        XSLFTextShape title2=titleBodySlide.getPlaceholder(0);
        title2.setText("Second Title");

        XSLFTextShape body2=titleBodySlide.getPlaceholder(1);
        body2.clearText(); // unset any existing text
        body2.addNewTextParagraph().addNewTextRun().setText("First paragraph");
        body2.addNewTextParagraph().addNewTextRun().setText("Second paragraph");
        body2.addNewTextParagraph().addNewTextRun().setText("Third paragraph");

        List<XSLFSlide> slides=ppt.getSlides();
        slides.forEach(System.out::println);
        System.out.println(slides.size());

        try (FileOutputStream out = new FileOutputStream("D:\\data\\slideshow.pptx");){
            ppt.write(out);
            ppt.close();
        }
    }
}
