package com.brillilab.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AsyncClientHandler{

    protected AsynchronousSocketChannel client;
    protected String host;
    protected int port;

    public AsyncClientHandler(String host,int port) {
        this.host=host;
        this.port=port;
        try {
            this.client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doConnect(){
        ByteBuffer readBuffer=ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
        client.connect(new InetSocketAddress(host,port),client,new ConnectCompletionHandler(readBuffer,writeBuffer));
    }

}
