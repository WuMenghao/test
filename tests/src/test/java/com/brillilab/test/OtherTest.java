package com.brillilab.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
}
