package com.brillilab.test;


import com.alibaba.excel.metadata.Sheet;
import com.brillilab.easyexcel.EasyExcelBean;
import com.brillilab.entity.excel.MyExcel;
import org.junit.Test;

import java.util.List;

public class EasyExcelTest {

   @Test
    public void read(){
       EasyExcelBean<MyExcel> instance=EasyExcelBean.getInstance();
       List<MyExcel> read=instance.read(MyExcel.class,new Sheet(1,1));
       read.forEach(System.out::println);
   }
}
