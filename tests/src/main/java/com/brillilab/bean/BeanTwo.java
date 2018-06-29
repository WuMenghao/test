package com.brillilab.bean;

import org.springframework.stereotype.Component;

@Component
public class BeanTwo implements Bean{

    public BeanTwo() {}

    public void show(){
        System.out.println("BwanTwo的方法");
    }
}
