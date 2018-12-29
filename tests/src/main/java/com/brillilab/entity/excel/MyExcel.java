package com.brillilab.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class MyExcel extends BaseRowModel {
    @ExcelProperty(index=0)
    private Long id;
    @ExcelProperty(index=1)
    private Integer num;
    @ExcelProperty(index=2)
    private Integer total;
    @ExcelProperty(index=3)
    private String text;
}
