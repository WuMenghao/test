package com.brillilab.test;

import com.brillilab.aop.ITarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

public class AOPTest {


    @Test
    public void test01(){
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("aop.xml");
        ITarget target = (ITarget)context.getBean("proxyFactoryBean");
        target.doTest();
    }
}
