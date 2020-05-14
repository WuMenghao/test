package com.brillilab.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Plasmid {

    @ExcelProperty(index=0)
    private Long id;
    @ExcelProperty(index=1)
    private String name;
    @ExcelProperty(index=2)
    private String classify;
    @ExcelProperty(index=3)
    private String atlas;
    @ExcelProperty(index=4)
    private String resistance;
    @ExcelProperty(index=5)
    private String inducingMethod;
    @ExcelProperty(index=6)
    private String zhbd;
    @ExcelProperty(index=7)
    private String ydff;
    @ExcelProperty(index=8)
    private String syxl;
    @ExcelProperty(index=9)
    private String ztmz;
    @ExcelProperty(index=10)
    private String jczl;
    @ExcelProperty(index=11)
    private String bdbq;
    @ExcelProperty(index=12)
    private String qdyz;
    @ExcelProperty(index=13)
    private String zcx;
    @ExcelProperty(index=14)
    private String zllx;
    @ExcelProperty(index=15)
    private String yxkz;
    @ExcelProperty(index=16)
    private String klsz;
    @ExcelProperty(index=17)
    private String bzbj;
    @ExcelProperty(index=18)
    private String kbsp;
    @ExcelProperty(index=19)
    private String syyw;
    @ExcelProperty(index=20)
    private String gs;
    @ExcelProperty(index=21)
    private String bdsz;
}
