package com.brillilab.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

public class RedisUtils {

    private static final JedisPool jedisPool = null;

    static {
        new GenericObjectPoolConfig();

    }

}
