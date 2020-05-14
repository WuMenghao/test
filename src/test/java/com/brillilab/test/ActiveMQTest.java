package com.brillilab.test;

import com.brillilab.activemq.dao.ActiveMQDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/activemq.xml"})
public class ActiveMQTest {

    @Autowired
    ActiveMQDao activeMQDao;

    /**
     * 测试发送信息到队列
     */
    @Test
    public void sendMessage(){
        activeMQDao.sendMessage("{name:'wumenghao',age:16}");
    }

    /**
     * 测试从队列接收信息
     */
    @Test
    public void reciveMessage(){
        boolean flag=true;
        while (flag){
            TextMessage receive = activeMQDao.receive();
            try{
                if (receive!=null){
                    flag=false;
                    System.out.println("收到了消息：\t" + receive.getText());
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 测试多线程发送信息
     */
    @Test
    public void ConcurrentSendMessage() throws InterruptedException {
        AtomicInteger ai=new AtomicInteger(0);

        this.createThread(ai).start();
        this.createThread(ai).start();
        this.createThread(ai).start();

        Thread.sleep(10000L);

    }


    /**
     * 不断接收消息
     */
    @Test
    public void receiveForever(){
        while (true){
            TextMessage receive = activeMQDao.receive();
            if (receive!=null){
                try {
                    System.out.println("收到了消息：\t" + receive.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 线程创建的方法
     * @param ai
     * @return
     */
    private Thread createThread(AtomicInteger ai){
        return  new Thread(() -> {
            while (ai.get()<100){
                activeMQDao.sendMessage("Thread:"+Thread.currentThread().getName()+"----{name:'wumenghao',age:16,num:"+ai.get()+"}");
                ai.getAndIncrement();
            }
        });
    }
}
