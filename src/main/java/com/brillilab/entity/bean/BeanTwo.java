package com.brillilab.entity.bean;

import org.springframework.stereotype.Component;

@Component
public class BeanTwo implements Bean{

    public BeanTwo() {}

    @Override
    public void show(){
        System.out.println("BwanTwo的方法");
    }
}
