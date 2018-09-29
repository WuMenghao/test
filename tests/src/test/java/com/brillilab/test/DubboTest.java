package com.brillilab.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.brillilab.dubbo.ITestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 模拟Consumer获取远程服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/dubbo-client.xml"})
public class DubboTest {

    @Reference
    ITestService iTestService;

    @Test
    public void test01(){
        String wmh = iTestService.sayHello("wmh");
        System.out.println(wmh);
    }
}
