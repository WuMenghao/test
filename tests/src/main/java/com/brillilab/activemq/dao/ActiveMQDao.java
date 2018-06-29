package com.brillilab.activemq.dao;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * ActiveMQ 简单的DAO
 *
 * @author wmh
 */
@Repository
public class ActiveMQDao {

    @Resource(name = "queueTemplate")
    private JmsTemplate queueTemplate;

    /**
     * 发送文本消息
     * @param destination
     * @param message
     */
    public void sendMessage(String destination,String message){
        System.out.println(Thread.currentThread().getName()+" 向队列"+destination.toString()+"发送消息---------------------->"+message);
        queueTemplate.send(destination ,(MessageCreator) session -> session.createTextMessage(message));
    }

    public void sendMessage(String message){
        System.out.println(Thread.currentThread().getName()+" 向队列"+ queueTemplate.getDefaultDestinationName()+"发送消息---------------------->"+message);
        queueTemplate.send((MessageCreator) session -> session.createTextMessage(message));
    }


    /**
     * 接收文本消息
     * @param destination
     * @return
     */
    public TextMessage receive(String destination){
        TextMessage textMessage = (TextMessage) queueTemplate.receive(destination);
        try{
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return textMessage;
    }

    public TextMessage receive(){
        TextMessage textMessage = (TextMessage) queueTemplate.receive(queueTemplate.getDefaultDestination());
        try{
            if (textMessage!=null){
                System.out.println("从队列" + queueTemplate.getDefaultDestination().toString()+ "收到了消息：\t"
                        + textMessage.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return textMessage;
    }

}
