package com.brillilab.test;

import com.brillilab.utils.QRCodeEncoderUtils;
import com.brillilab.utils.SecureUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    @Test
    public void test07(){
        Assert.assertEquals(Md5Crypt.md5Crypt("123456".getBytes()),Md5Crypt.md5Crypt("123456".getBytes()));
    }

    @Test
    public void test08() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Assert.assertEquals(SecureUtils.MD5Encrypt("123456"),SecureUtils.MD5Encrypt("123456"));
    }

    @Test
    public void test09(){
        System.out.println(1000 << 2);
    }

    @Test
    public void test10(){
        Integer iv = 100;
        long lv = iv;
        System.out.println(lv);
    }
    
    @Test
    public void test11(){
        BigDecimal v1=new BigDecimal(1);
        BigDecimal v2=new BigDecimal(-5);
        BigDecimal add=v1.add(v2);
        System.out.println(add);
        System.out.println(v2.compareTo(new BigDecimal(0)));
    }
}
