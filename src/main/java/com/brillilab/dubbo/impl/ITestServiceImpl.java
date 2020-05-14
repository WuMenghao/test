package com.brillilab.dubbo.impl;

import com.brillilab.dubbo.ITestService;

/**
 * dubbo service 实现类
 */
public class ITestServiceImpl implements ITestService {

    public String sayHello(String name){
        return "Hi " + name + ", I am dubbo service";
    }
}
