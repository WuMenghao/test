package com.brillilab.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectCompletionHandler implements CompletionHandler<Void,AsynchronousSocketChannel> {


    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer;

    public ConnectCompletionHandler(ByteBuffer readBuffer,ByteBuffer writeBuffer) {
        this.readBuffer=readBuffer;
        this.writeBuffer=writeBuffer;
    }

    @Override
    public void completed(Void result,AsynchronousSocketChannel channel) {

        ThreadPoolExecutor executor=new ThreadPoolExecutor(5,10,
                0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        //读监听
        new Thread(() -> {
            channel.read(readBuffer,readBuffer,new ReadCompletionHandler(channel));
        }).start();
        //写监听
        new Thread(()->{
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNext()){
                String next=scanner.next();
                try {
                    //内容放入缓冲区
                    byte[] content=next.getBytes("UTF-8");
                    writeBuffer.clear();
                    writeBuffer.put(content);
                    //设置读写起始结束位置
                    writeBuffer.position(0);
                    writeBuffer.limit(content.length);
                    //开始写监听
                    channel.write(writeBuffer,writeBuffer,new WriteCompletionHandler(channel));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void failed(Throwable exc,AsynchronousSocketChannel channel) {
        exc.printStackTrace();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
