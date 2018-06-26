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
        FileSystemXmlApplicationContext context=new FileSystemXmlApplicationContext("D:\\GitHub\\test\\tests\\src\\main\\resources\\beans.xml");
        Bean beanTwo = (Bean)context.getBean("beanTwo");
        beanTwo.show();
        Assert.assertNotNull(beanTwo);
    }
}
