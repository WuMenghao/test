package com.brillilab.test;

import com.brillilab.utils.QRCodeEncoderUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;

public class OtherTest {

    private int num=0;

    //并不会报异常
    @Test
    public void test01(){
        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        increace();
                        System.out.println("Thread:"+Thread.currentThread().getName()+"  start!   num="+num);
                    }
                }
            }).start();
        }
    }

    public synchronized void increace(){
        num++;
    }

    @Test
    public void test02(){
        List<String> list=new ArrayList<>();
        int i=0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }

    @Test
    public void test03(){
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toBinaryString(8));
        System.out.println(10&8);
    }

    @Test
    public void test04() throws Exception {
        String url="https://www.baidu.com/";
        QRCodeEncoderUtils.encoder(url);
    }

    @Test
    public void test05(){
        double a=0.1*0.1;

        BigDecimal b = new BigDecimal(a);
        b.setScale(2,RoundingMode.HALF_UP);

        DoubleAccumulator da=new DoubleAccumulator(((l,r)->{
            return l*r;
        }),0.1);
        da.accumulate(0.1);
        double v = da.doubleValue();


        System.out.println(a);
        System.out.println(b.floatValue());
        System.out.println(v);
    }

    @Test
    public void test06(){
        System.out.println(1 & 2);
    }
}
