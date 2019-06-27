package com.brillilab.test;

import com.brillilab.aio.client.AsyncClientHandler;
import com.brillilab.aio.server.AsyncServerHandler;
import org.junit.Test;

import java.util.Scanner;

public class AIOTest {

    @Test
    public void socketServer(){
        String host = "172.16.5.230";
        int port = 8001;
        AsyncServerHandler asyncServerHandler=new AsyncServerHandler(host,port);
        asyncServerHandler.start();
        while (true){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void socketClient(){
        String host = "localhost";
        int port = 8001;
        AsyncClientHandler asyncClientHandler=new AsyncClientHandler(host,port);
        asyncClientHandler.doConnect();
    }

    @Test
    public void scanner() {

        //创建Scanner对象
        //System.in表示标准化输出，也就是键盘输出
        Scanner sc = new Scanner(System.in);
        //利用hasNextXXX()判断是否还有下一输入项
        while (sc.hasNext()) {
            //利用nextXXX()方法输出内容
            String str = sc.next();
            System.out.println(str);
        }
    }
}
