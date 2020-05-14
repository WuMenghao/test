package com.brillilab.aio.server;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

@Data
public class AsyncServerHandler{

    protected String host;
    protected int port;

    protected AsynchronousServerSocketChannel socketChannel;

    public AsyncServerHandler(String host,int port) {

        this.host=host;
        this.port=port;

        try {
            //打开channel
            socketChannel = AsynchronousServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(this.host,this.port));
            System.out.println("service start ！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开始
     */
    public synchronized void start(){
        doAccept();
    }

    /**
     * 接收数据
     */
    public void doAccept(){
        socketChannel.accept(this,new AcceptCompletionHandler());
    }
}
