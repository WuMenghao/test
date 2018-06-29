package com.brillilab.test;

import com.brillilab.bean.Bean;
import com.brillilab.bean.BeanOne;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class FileSystemXmlApplicationContextTest {

    @Test
    public void test01(){
        //FileSystemXmlApplicationContext的初始化与对象创建
        FileSystemXmlApplicationContext context=new FileSystemXmlApplicationContext("D:\\GitHub\\test\\tests\\src\\main\\resources\\beans.xml");

        BeanOne beanOne = (BeanOne) context.getBean("beanOne");
        beanOne.showAll();

        //FileSystemXmlApplicationContext的关闭
        context.close();

        Assert.assertNotNull(beanOne);
    }

    @Test
    public void test02(){
        //FileSystemXmlApplicationContext的初始化与对象创建
        FileSystemXmlApplicationContext context=new FileSystemXmlApplicationContext("D:\\GitHub\\test\\tests\\src\\main\\resources\\beans1.xml");

        BeanOne beanOne = (BeanOne) context.getBean("beanOne");
        beanOne.showAll();

        //FileSystemXmlApplicationContext的关闭
        context.close();

        Assert.assertNotNull(beanOne);
    }
}
