package com.brillilab.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HelloClient {

    public void connect(String host,int port){

        NioEventLoopGroup group=new NioEventLoopGroup();

        Bootstrap boot=new Bootstrap();
        boot.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY,true)
            .handler();
    }

    class HelloClientChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new );
        }
    }

    class HelloClientHandler extends ChannelInboundHandlerAdapter{

    }
}
