package com.brillilab.blockingQueue;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {

    private volatile boolean isRunning=true;
    private static final long SLEEP_TIME=1;
    private BlockingQueue queue;
    private AtomicInteger index=new AtomicInteger(0);

    private List data;

    public Producer(List data,BlockingQueue queue) {
        this.data=data;
        this.queue=queue;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (index.get() == (data.size()-1)){
                    stop();
                }
                queue.offer(data.get(index.get()),2L,TimeUnit.SECONDS);
                index.incrementAndGet();
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }

}
