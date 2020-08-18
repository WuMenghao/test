package com.brillilab.test;

import org.junit.Before;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JdkTest {

    private volatile boolean flag = true;

    private ThreadPoolExecutor executor;

    @Before
    public void initExcutor(){
         executor = new ThreadPoolExecutor(
                5, 20,60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(30),new ThreadPoolExecutor.CallerRunsPolicy());
    }

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

    /**
     * 强引用，弱引用，软引用，虚引用
     */
    @Test
    public void reference(){
        ReferenceQueue queue = new ReferenceQueue();
        String str = new String("sr");
        WeakReference<String> weakReference = new WeakReference<>(new String("wr"));
        SoftReference<String> softReference = new SoftReference<>(new String("sr"));
        PhantomReference<String> phantomReference = new PhantomReference<String>(new String("pr"), queue);
        System.gc();
        System.out.println(str);
        System.out.println(weakReference.get());
        System.out.println(softReference.get());
        System.out.println(phantomReference.get());
    }

    @Test
    public void executor01() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1,60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i<120 ; i++){
            final int j = i;
            executor.submit(() -> {
                System.out.println(j);
                if (j==119){
                    executor.shutdown();
                }
            });
        }
        boolean b = executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(b);
    }

    @Test
    public void countDownLatch() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5, 20,60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(30),new ThreadPoolExecutor.CallerRunsPolicy());
        int worker = 3;
        CountDownLatch countDownLatch = new CountDownLatch(worker);
        executor.submit(() -> {
            System.out.println("D is waiting for other three threads");
            try {
                countDownLatch.await();
                System.out.println("All done, D starts working");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (char threadName='A'; threadName <= 'C'; threadName++) {
            final String tN = String.valueOf(threadName);
            executor.submit(() -> {
                System.out.println(tN + "is working");
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(tN + "finished");
                countDownLatch.countDown();
            });
        }
        if(!executor.awaitTermination(10, TimeUnit.SECONDS)){
            System.out.println(executor.isTerminated());
            executor.shutdown();
        }
    }

    @Test
    public void volatileTest() throws InterruptedException {
        executor.submit(() -> {
            while (flag){
                System.out.println(flag);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
            System.out.println("set flag == false");
        });
        executor.awaitTermination(5,TimeUnit.SECONDS);
    }

    @Test
    public void testInitMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("1","哈哈哈");
        map.put("2","哈哈哈");
        map.put("3","哈哈哈");

        // 错误的删除方法 java.util.ConcurrentModificationException
        //  map.forEach((k,v) -> {
        //   map.remove(k);
        // });

        // 正确的删除方法 modCount++
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();;
        }
        System.out.println(map);
    }
}
