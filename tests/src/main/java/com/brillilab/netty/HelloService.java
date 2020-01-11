package com.brillilab.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class HelloService {

    private int port;

    public HelloService(int port){
        this.port=port;
    }

    public void run(){
        //The first one, often called 'boss', accepts an incoming connection.
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        //The second one, often called 'worker', handles the traffic of the accepted connection once the boss accepts the connection
        // and registers the accepted connection to the worker.
        NioEventLoopGroup workerGroup=new NioEventLoopGroup();

        //ServerChannel启动器
        ServerBootstrap boot=new ServerBootstrap();
        boot.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new HelloServerChannelInitializer());

        try {
            ChannelFuture future=boot.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    class HelloServerChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new DispatcherServerHandler());
        }
    }

    class DispatcherServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            try {
                //读取
                ByteBuf buf = (ByteBuf) msg;
                byte[] req=new byte[buf.readableBytes()];
                buf.readBytes(req);

                //结果
                String body = new String(req);
                System.out.println(body);

                //写回
                ByteBuf resp=Unpooled.copiedBuffer((body+"\n").getBytes("utf-8"));
                ctx.write(resp);
                ctx.flush();
            }finally {
                ReferenceCountUtil.release(msg);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

    }
}
