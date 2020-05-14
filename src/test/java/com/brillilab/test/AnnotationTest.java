package com.brillilab.test;

import com.brillilab.annotation.SimpleClassPathXMLApplicationContext;
import com.brillilab.entity.bean.BeanOne;
import org.junit.Test;

public class AnnotationTest {

    @Test
    public void test(){
        SimpleClassPathXMLApplicationContext context=new SimpleClassPathXMLApplicationContext("beans.xml");
        BeanOne beanOne=(BeanOne) context.getBean("beanOne");
        beanOne.showAll();
    }
}
