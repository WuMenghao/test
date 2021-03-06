package com.brillilab.test;

import com.brillilab.redis.DistributedLock;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class RedisTests {

    private final String LOCK = "lock_test";
    private ThreadPoolExecutor threadPool;
    private Integer count = 0;

    @Before
    public void prepareThreadPool(){
        threadPool = new ThreadPoolExecutor(
                20,
                200,
                60,
                TimeUnit.SECONDS,new LinkedBlockingDeque<>(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Test
    public void redisLockTest() throws InterruptedException {


        threadPool.execute(() ->{
            String lockVersion = DistributedLock.lock(LOCK);
            System.out.println("Lock one ok");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = DistributedLock.unLock(LOCK, lockVersion);
            System.out.printf("Unlock one result : %s \n",b);
        });

        threadPool.execute(() ->{
            String lockVersion = DistributedLock.lock(LOCK);
            System.out.println("Lock two ok");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = DistributedLock.unLock(LOCK, lockVersion); //超时 false
            System.out.printf("Unlock two result : %s \n",b);
        });

        threadPool.awaitTermination(50,TimeUnit.SECONDS);
    }

    @Test
    public void redisLockTest2() throws InterruptedException {


        for (int i=0;i<1000;i++){
            threadPool.execute(() ->{
                String lockVersion = DistributedLock.lock(LOCK);
                System.out.println(count);
                count++;
                DistributedLock.unLock(LOCK, lockVersion);
            });
        }


        threadPool.awaitTermination(50,TimeUnit.SECONDS);
    }

    @Test
    public void ReentrantLockTest2() throws InterruptedException {


        ReentrantLock lock = new ReentrantLock();
        for (int i=0;i<1000;i++){
            threadPool.execute(() ->{
                lock.lock();
                System.out.println(count);
                count++;
                lock.unlock();
            });
        }


        threadPool.awaitTermination(3,TimeUnit.SECONDS);
    }
}
