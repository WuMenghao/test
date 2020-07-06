package com.brillilab.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

    private static final long SLEEP_TIME=1;
    private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue=queue;
    }

    @Override
    public void run() {
        try {
            boolean isRunning=true;
            while (isRunning) {
                Object poll=queue.poll(2,TimeUnit.SECONDS);
                if(poll!=null){
                    System.out.println(poll);
                    Thread.sleep(SLEEP_TIME);
                }else {
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
