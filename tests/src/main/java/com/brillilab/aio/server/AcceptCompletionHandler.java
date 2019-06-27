package com.brillilab.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncServerHandler> {

    public AcceptCompletionHandler() {
    }

    @Override
    public void completed(AsynchronousSocketChannel channel,AsyncServerHandler attachment) {
        attachment.socketChannel.accept(attachment,this);
        ByteBuffer buffer=ByteBuffer.allocate(512);
        channel.read(buffer,buffer,new ReadWriteCompletionHandler(channel));
    }

    @Override
    public void failed(Throwable exc,AsyncServerHandler attachment) {
        exc.printStackTrace();
    }
}
