package com.brillilab.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloService {

    public void bind(int port){
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        NioEventLoopGroup workerGroup=new NioEventLoopGroup();

        //ServerChannel启动器
        ServerBootstrap boot=new ServerBootstrap();
        boot.group(bossGroup,workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG,1024)
            .childHandler(new HelloServerChannelHandler());
    }

    class HelloServerChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimServerHandler());
        }
    }

    class TimServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            //读取
            ByteBuf buf = (ByteBuf) msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);

            //结果
            String body = new String(req,"utf-8");
            System.out.println(body);

            //写回
            ByteBuf resp=Unpooled.copiedBuffer("hello this is server!".getBytes("utf-8"));
            ctx.write(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
