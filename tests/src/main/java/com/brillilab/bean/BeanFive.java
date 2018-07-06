package com.brillilab.bean;

public class BeanFive implements Bean2{

    public BeanFive() {}

    public void show(){
        System.out.println("BeanFive的方法");
    }

    @Override
    public String toString() {
        return "BeanFive{}";
    }
}
