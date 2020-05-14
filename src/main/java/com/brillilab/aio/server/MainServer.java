package com.brillilab.aio.server;

public class MainServer {
    public static void main(String[] args){
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
}
