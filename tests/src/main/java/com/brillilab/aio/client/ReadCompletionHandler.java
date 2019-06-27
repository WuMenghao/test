package com.brillilab.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if(this.channel == null){
            this.channel=channel;
        }
    }


    @Override
    public void completed(Integer result,ByteBuffer buffer) {
        if(result!=-1){
            buffer.flip();
            byte[] bytes=new byte[buffer.remaining()];
            buffer.clear();
            try {
                String sreq=new String(bytes,"UTF-8");
                System.out.printf("服务器响应: %s %n",sreq.trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        channel.read(buffer,buffer,this);
    }

    @Override
    public void failed(Throwable exc,ByteBuffer buffer) {
        exc.printStackTrace();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
