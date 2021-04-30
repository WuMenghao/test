package com.brillilab.redis;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.xmlbeans.impl.tool.CodeGenUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DistributedLock {

    private static JedisPool jedisPool;

    private static final String LOCK_SUCCESS = "OK";
    private static final Integer UNLOCK_SUCCESS = 1;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int DEFAULT_EXPIRE_TIME = 20;

    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(200);
//        config.setMinIdle(10*1000);
        config.setTestOnReturn(true);
        jedisPool=new JedisPool(config,
                "localhost",
                6379,
                1000*10,
                null,
                10,
                "test_client;",
                false);
    }

    /**
     * 获取锁
     * @param lockName
     */
    public static String lock(String lockName){
        Jedis jedis = null;
        try {
            String lockVersion = UUID.randomUUID().toString();
            jedis = jedisPool.getResource();
            // 自旋获取锁
            while (true){
                // set EX 过期时间 NX 不存在key才进行设置
                // EX 设置过期时间防止死锁
                // NX 防止锁覆盖
                SetParams setParams = new SetParams().nx().ex(DEFAULT_EXPIRE_TIME);
                String result = jedis.set(lockName, lockVersion, setParams);
                if (LOCK_SUCCESS.equals(result)){
                    return lockVersion;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != jedis){
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 释放锁
     * @param lockName
     * @param lockVersion
     * @return
     */
    public static boolean unLock(String lockName, String lockVersion){
        try (Jedis jedis = jedisPool.getResource()) {
            // watch监控key,若有其他事务修改key-value,事务不会被执行
            // 防止锁被错误释放
            jedis.watch(lockName);
            // 检验锁版本，判断是否持有锁
            if (lockVersion.equals(jedis.get(lockName))) {
                // 开启事务
                Transaction multi = jedis.multi();
                // 释放锁
                multi.del(lockName);
                // 提交
                List<Object> result = multi.exec();
                if (CollectionUtils.isNotEmpty(result)) {
                    return UNLOCK_SUCCESS.equals(result.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
