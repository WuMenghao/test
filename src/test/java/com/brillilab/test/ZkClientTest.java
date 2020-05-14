package com.brillilab.test;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.junit.Test;

import java.util.List;

public class ZkClientTest {

    private static final String CONNECT_ADDR="127.0.0.1:2181";

    private static final Integer SESSION_TIMEOUT=10000;

    /**
     * 测试Zookeeper连接
     */
    @Test
    public void test01(){
        ZkClient zkClient=new ZkClient(CONNECT_ADDR,SESSION_TIMEOUT);

        List<String> children = zkClient.getChildren("/");
        children.forEach(e->{
            System.out.println(e);
        });

        zkClient.close();
    }
}
