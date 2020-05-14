package com.brillilab.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
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
}
