package com.brillilab.aio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class WriteCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public WriteCompletionHandler(AsynchronousSocketChannel channel) {
        if(this.channel == null){
            this.channel=channel;
        }
    }

    @Override
    public void completed(Integer result,ByteBuffer buffer) {
        try {
            //获取内容
            buffer.flip();
            byte[] body=new byte[buffer.remaining()];
            buffer.get(body);
            String sreq=new String(body,"UTF-8");
            System.out.print(sreq.trim()+"\n");

            //写内容到Server
            doWrite(sreq.trim()+"\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc,ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写逻辑
     * @param content
     */
    private void doWrite(String content){
        if(content!=null && content.trim().length()>0){
            byte[] bytes=content.getBytes();
            ByteBuffer buffer=ByteBuffer.wrap(bytes);
            channel.write(buffer);
            buffer.clear();
        }
    }
}
