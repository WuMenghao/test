package com.brillilab.aio.client;

public class MainClient {
    public static void main(String[] args){
        String host = "172.16.5.230";
        int port = 8001;
        AsyncClientHandler asyncClientHandler=new AsyncClientHandler(host,port);
        asyncClientHandler.doConnect();
        while (true){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
