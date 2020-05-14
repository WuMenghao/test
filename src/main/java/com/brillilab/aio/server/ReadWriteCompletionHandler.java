package com.brillilab.aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadWriteCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {

    protected AsynchronousSocketChannel channel;

    public ReadWriteCompletionHandler(AsynchronousSocketChannel channel) {
        if(this.channel == null){
            this.channel=channel;
        }
    }

    @Override
    public void completed(Integer result,ByteBuffer buffer) {
        //判断是否读取完
        if(buffer.hasRemaining()){

            //进行写操作
            buffer.flip();
            byte[] body=new byte[buffer.remaining()];
            buffer.get(body,0,result);
            try {
                String sreq=new String(body,"UTF-8");
                System.out.print(sreq.trim()+"\n");
                doWrite(sreq.trim()+"\n");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        buffer.clear();
        channel.read(buffer,buffer,this);

        //如果buffer是满的，继续读取写出，否则关闭输入输出
//        if(!hasRemaining){
//            buffer.clear();
//            channel.read(buffer,buffer,this);
//        }else {
//            try {
//                channel.shutdownInput();
//                channel.shutdownOutput();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

    @Override
    public void failed(Throwable exc,ByteBuffer attachment) {
        exc.printStackTrace();
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
