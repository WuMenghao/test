package com.brillilab.test;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.modelbuild.ModelBuildEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.brillilab.entity.excel.MyExcel;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyExcelTest {

    @Test
    public void read(){
        try (InputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xlsx")){

//            List<Object> read=EasyExcelFactory.read(in,new Sheet(1,1,MyExcel.class));
//            read.forEach(System.out::println);

            List<MyExcel> myExcels = new ArrayList<>();

            ExcelReader excelReader = new ExcelReader(in,null,new AnalysisEventListener() {
                @Override
                public void invoke(Object object,AnalysisContext analysisContext) {
                    Method[] methods=MyExcel.class.getMethods();


                    System.out.println(object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            });

            excelReader.read(new Sheet(1,1));
            List<Sheet> sheets=excelReader.getSheets();

            sheets.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class ExcelListener extends AnalysisEventListener{



        @Override
        public void invoke(Object o,AnalysisContext analysisContext) {

        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }
    }
}
