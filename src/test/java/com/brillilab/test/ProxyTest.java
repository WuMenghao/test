package com.brillilab.test;

import com.brillilab.entity.bean.Bean2;
import com.brillilab.entity.bean.BeanFive;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        //创建被代理对象
        BeanFive beanFive = BeanFive.class.newInstance();

        //创建代理对象
        Bean2 proxy=(Bean2) Proxy.newProxyInstance(BeanFive.class.getClassLoader(),new Class[]{Bean2.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(beanFive,args);
                return null;
            }
        });

        //进行代理
        proxy.show();
    }
}
