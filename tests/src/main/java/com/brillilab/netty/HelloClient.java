package com.brillilab.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloClient {

    private String host;
    private int port;

    public HelloClient(String host,int port) {
        this.host=host;
        this.port=port;
    }

    public void connect(){

        NioEventLoopGroup group=new NioEventLoopGroup();

        Bootstrap boot=new Bootstrap();
        boot.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY,true)
            .handler(new HelloClientChannelInitializer());

        try {
            ChannelFuture future=boot.connect(host,port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    //ChannelInitializer
    class HelloClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new HelloClientHandler());
        }
    }

    //ChannelInboundHandlerAdapter
    class HelloClientHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            final ByteBuf time = ctx.alloc().buffer(4);
            String s=String.valueOf((int) (System.currentTimeMillis() / 1000L + 2208988800L));
            time.writeBytes(s.getBytes("UTF-8"));

            final ChannelFuture f = ctx.writeAndFlush(time);
            f.addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture future) {
                    assert f == future;
                    ctx.close();
                }
            });
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            //读取
            ByteBuf buf = (ByteBuf) msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);

            //结果
            String body = new String(req,"utf-8");
            System.out.println(body);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
            log.warn("Unexpected exception from downstream :{}",cause.getMessage());
            ctx.close();
        }
    }
}
