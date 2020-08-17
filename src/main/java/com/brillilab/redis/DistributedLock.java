package com.brillilab.redis;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.xmlbeans.impl.tool.CodeGenUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;
import java.util.UUID;

public class DistributedLock {

    private static JedisPool jedisPool;

    private static final String SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int DEFAULT_EXPIRE_TIME = 15;

    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(20);
        config.setMinIdle(10);
        jedisPool=new JedisPool(config,"localhost",6379,1000);
    }

    /**
     * 获取锁
     * @param lockName
     */
    public static void lock(String lockName){
        Jedis jedis = null;
        try {
            String randomValue = UUID.randomUUID().toString();
            jedis = jedisPool.getResource();
            while (true){
                String result = jedis.setex(lockName,DEFAULT_EXPIRE_TIME, randomValue);
                if (SUCCESS.equals(result)){
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }
}
