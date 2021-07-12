package com.brillilab.test;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Integer.valueOf;

public class AQSTest {

    @Test
    public void testArrayBlockQueue() throws InterruptedException {
        ArrayBlockingQueue<Object> objects = new ArrayBlockingQueue<>(5);

        Object poll = objects.take();

        Thread.sleep(1000);
        for (int i = 0; i<6;i++){
            Integer integer = valueOf(i);
            objects.offer(integer);
        }

        System.out.println(poll);
    }
}
