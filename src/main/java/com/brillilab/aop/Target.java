package com.brillilab.aop;

public class Target implements ITarget{

    public Target() {
    }

    @Override
    public void doTest(){
        System.out.println("我的方法");
    }

}
