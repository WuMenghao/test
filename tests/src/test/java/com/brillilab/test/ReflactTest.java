package com.brillilab.test;

import com.brillilab.entity.pojo.SCategory;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * 反射测试
 */
public class ReflactTest {

    /**
     * 字段设置值
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        Class<SCategory> clazz=SCategory.class;
        SCategory sCategory=clazz.newInstance();

        Field[] fs=clazz.getDeclaredFields();
        for (Field f: fs) {
            f.setAccessible(true);
            if(f.getType().equals(String.class)){
                f.set(sCategory,"hhh");
            }
        }

        System.out.println(sCategory);
    }
}
