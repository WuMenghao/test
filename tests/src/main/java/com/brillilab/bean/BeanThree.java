package com.brillilab.bean;

import org.springframework.stereotype.Component;

@Component
public class BeanThree implements Bean{

    public BeanThree() {}

    @Override
    public void show(){
        System.out.println("BwanThree的方法");
    }
}
