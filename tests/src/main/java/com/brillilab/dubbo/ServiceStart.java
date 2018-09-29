package com.brillilab.dubbo;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

public class ServiceStart {

    /**
     * 启动类 模拟dubbo providor
     * @param args
     */
    public static void main(String[] args){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml");

        System.out.println("start dubbo");

        while (true){
            Thread.yield();
        }
    }
}
