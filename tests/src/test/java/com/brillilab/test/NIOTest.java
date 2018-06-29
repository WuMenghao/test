package com.brillilab.test;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  NIO的测试
 *
 * @author  wmh
 */
public class NIOTest {

    /**
     * 测试NIO文件读取
     * @throws IOException
     */
    @Test
    public void readFileByNIO() throws IOException {
        //获取输入流
        FileInputStream inputStream=new FileInputStream("D:\\data\\files\\nio.txt");
        //获取Channel
        FileChannel channel = inputStream.getChannel();
        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据到缓冲区
        int read = channel.read(buffer);
        //重设buffer
        buffer.flip();

        //从buffer中取出数据
        byte[] bytes=new byte[1024];
        int i=0;
        while (buffer.hasRemaining()){
            byte b = buffer.get();
            bytes[i]=b;
            i++;
        }
        String s=new String(bytes,"utf-8");
        System.out.print(s);

        //关流释放资源
        inputStream.close();
    }

    /**
     * 测试NIO文件传输
     * @throws IOException
     */
    @Test
    public void transferFileByNIO() throws IOException {

        long begian = System.currentTimeMillis();

        //创建流
        FileInputStream inputStream=new FileInputStream("D:\\data\\files\\ideaIU-2018.1.2.exe");
        FileOutputStream outputStream=new FileOutputStream("D:\\data\\files\\ideaIU-2018.1.2-copy.exe");

        //获取Channel
        FileChannel channelIn=inputStream.getChannel();
        FileChannel channelOut = outputStream.getChannel();

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读入
        int i=0;
        while (channelIn.read(buffer)!=-1){
            //重设Buffer
            buffer.flip();
            //写出
            i+= channelOut.write(buffer);
            //清空Buffer
            buffer.clear();
        }


        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        System.out.println("一共传送数据："+i/1000+"KB");
        System.out.println("传输文件所用时间："+(end-begian)+"sm");
    }

    /**
     * 测试普通文件传输
     * @throws IOException
     */
    @Test
    public void transferFileNormal() throws IOException {

        long begian = System.currentTimeMillis();

        FileInputStream inputStream=new FileInputStream("D:\\data\\files\\ideaIU-2018.1.2.exe");
        FileOutputStream outputStream=new FileOutputStream("D:\\data\\files\\ideaIU-2018.1.2-copy.exe");

        byte[] baffer =new byte[1024];
        int i=0;
        while (inputStream.read(baffer)!=-1){
            outputStream.write(baffer);
        }

        inputStream.close();
        outputStream.close();

        long end = System.currentTimeMillis();

        System.out.println("传输文件所用时间："+(end-begian)+"sm");

    }
}
