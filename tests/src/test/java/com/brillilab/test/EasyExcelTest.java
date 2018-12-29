package com.brillilab.test;


import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.brillilab.easyexcel.EasyExcelBean;
import com.brillilab.entity.excel.MyExcel;
import com.brillilab.entity.excel.Plasmid;
import org.junit.Test;

import java.util.List;

public class EasyExcelTest {

   @Test
    public void read(){
       EasyExcelBean<MyExcel> instance=EasyExcelBean.getInstance();
       List<MyExcel> read=instance.read(MyExcel.class,new Sheet(1,1),ExcelTypeEnum.XLSX,"C:\\Users\\Administrator\\Desktop\\test.xlsx");
       read.forEach(System.out::println);
   }
    @Test
    public void read2(){
        EasyExcelBean<Plasmid> instance=EasyExcelBean.getInstance();
        List<Plasmid> read=instance.read(Plasmid.class,new Sheet(1,1),ExcelTypeEnum.XLS,"C:\\Users\\Administrator\\Desktop\\质粒模板库.xls");
        read.forEach(System.out::println);
    }
}
