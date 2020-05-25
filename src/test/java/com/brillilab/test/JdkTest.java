package com.brillilab.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class JdkTest {

    @Test
    public void methodTest(){
        System.out.printf("Integer.bitCount(2147483647):%s \t\n",Integer.bitCount(2147483647));
        System.out.printf("Integer.rotateLeft(2147483647,2):%s \t\n",Integer.rotateLeft(214,1));
        System.out.printf("Integer.rotateRight(214,1):%s \t\n",Integer.rotateRight(214,1));
        System.out.printf("Integer.toBinaryString(214):%s \t\n",Integer.toBinaryString(214));
        System.out.printf("Integer.toBinaryString(Integer.rotateLeft(214,1)):%s \t\n",Integer.toBinaryString(Integer.rotateLeft(214,1)));
        System.out.printf("Integer.toBinaryString(Integer.rotateRight(214,1)):%s \t\n",Integer.toBinaryString(Integer.rotateRight(214,1)));
        System.out.printf("Integer.toBinaryString(2147483647):%s \t\n",Integer.toBinaryString(2147483647));
        System.out.printf("Integer.toBinaryString(Integer.rotateLeft(2147483647,1)):%s \t\n",Integer.toBinaryString(Integer.rotateLeft(2147483647,1)));
        System.out.printf("Integer.toBinaryString(Integer.rotateRight(2147483647,1)):%s \t\n",Integer.toBinaryString(Integer.rotateRight(2147483647,1)));
        System.out.printf("Integer.decode(\"121212\"):%s \t\n",Integer.decode("121212"));
        System.out.printf("Integer.decode(\"0xFA44\"):%s \t\n",Integer.decode("0xFA44"));
        System.out.printf("Integer.parseInt(\"121212\"):%s \t\n",Integer.parseInt("121212"));
//        System.out.printf("Integer.parseInt(\"0xFA44\"):%s \t\n",Integer.parseInt("0xFA44")); //报错
        System.out.printf("Integer.valueOf(\"121212\"):%s \t\n",Integer.valueOf("121212"));
        System.out.printf("Integer.valueOf(121212):%s \t\n",Integer.valueOf(121212));
        final int iMax = Integer.MAX_VALUE;
        System.out.println(iMax + 1);
        System.out.println(Integer.toBinaryString(iMax + 1));
    }

    @Test
    public void timeTest(){
        System.out.printf("System.currentTimeMillis(): %s \t\n",System.currentTimeMillis());
        System.out.printf("new Date().getTime(): %s \t\n",new Date().getTime());
        System.out.printf("Calendar.getInstance().getTime().getTime(): %s \t\n",Calendar.getInstance().getTime().getTime());
        System.out.printf("Instant.now().toEpochMilli(): %s \t\n",Instant.now().toEpochMilli());
        System.out.printf("LocalDateTime.now().toInstant(ZoneOffset.of(\"+8\")).toEpochMilli(): %s \t\n",
                LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        LocalDateTime dt1 = LocalDateTime.now();
        LocalDateTime dt2 = dt1.plusSeconds(456788778);
        Duration duration = Duration.between(dt1, dt2);
        System.out.printf("Duration.between(dt1, dt2).getSeconds(): %s \t\n",duration.getSeconds());  // output:60
        System.out.printf("dt1.isBefore(dt2);: %s \t\n",dt1.isBefore(dt2));  // true
        System.out.printf("dt1.isAfter(dt2);: %s \t\n",dt1.isAfter(dt2));  // false


        LocalDate d1 = LocalDate.now();
        LocalDate d2 = d1.plusDays(2);
        Period period = Period.between(d1, d2);
        System.out.printf("Period.between(d1, d2).getDays(): %s \t\n",period.getDays());   //output:2
        System.out.printf("d1.isBefore(d2): %s \t\n",d1.isBefore(d2));   //true
        System.out.printf("d1.isAfter(d2): %s \t\n",d1.isAfter(d2));   //false

        //线程安全性
        //使用的便利性（如获取当前时间戳的便利性、增减日期的便利性等）
        //编写代码更简单优雅，如当前时间的格式化
        System.out.printf("LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")): %s \n\t",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    }

    @Test
    public void arrayTest(){
        int[] intArr = new int[3];
        String[] StrArr = new String[3];
        System.out.printf("int[] intArr = new int[3] : %s \t\n", Arrays.toString(intArr));
        System.out.printf("int[] intArr = new int[3] : %s \t\n", intArr);
        System.out.printf("String[] StrArr = new String[3] : %s \t\n", Arrays.toString(StrArr));
        System.out.printf("String[] StrArr = new String[3] : %s \t\n", StrArr);
    }

    @Test
    public void objectTest() throws CloneNotSupportedException {
        //在 Java 语言中，变量不能被重写。
        class A {
            public int x = 0;
            public  int y = 0;
            public void m() {
                System.out.print("A");
            }
        }
        class B extends A {
            public int x = 1;
            public  int y = 2;
            public void m() {
                System.out.print("B");
            }
        }
        A myClass = new B();
        System.out.print(myClass.x);
        System.out.print(myClass.y);
        myClass.m();

        //clone //深拷贝 嵌套对象需要手动进行
        class CloneTest implements Cloneable {
            int num;
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
        CloneTest ct = new CloneTest();
        ct.num = 666;
        CloneTest ct2 = (CloneTest) ct.clone();
        System.out.printf("clone: ct.num %s \t\n",ct.num);
        System.out.printf("clone: ct2.num %s \t\n",ct2.num);

        // 创建成员内部类
        class Outer {
            public Outer() {
                System.out.println("Outer Class.");
            }
            class Inner {
                public void sayHi() {
                    System.out.println("Hi, Inner.");
                }
            }
        }
        Outer out = new Outer();
        Outer.Inner inner = out.new Inner();
        inner.sayHi();

    }
}
