package com.brillilab.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
    private String host;
    private int port;
    private String groupName;
    private DefaultMQProducer producer;
    private String nameSrvAddr = "localhost:9876";
    private volatile static SyncProducer instance;

    private SyncProducer() {
    }

    private SyncProducer(String host, int port, String groupNam) {
        this.host = host;
        this.port = port;
        this.groupName = groupNam;
        this.producer = new DefaultMQProducer(groupNam);
        this.producer.setNamesrvAddr(nameSrvAddr);
    }

    public static SyncProducer getInstance(String host, int port, String groupName){
        if (instance  == null){
            synchronized (SyncProducer.class){
                if (instance == null){
                    instance = new SyncProducer(host, port, groupName);
                }
            }
        }
        return instance;
    }

    public SendResult sendMessage(String topic, String tag, String msg){
        Message message = null;
        try {
            message = new Message(topic, tag, msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
            return producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
