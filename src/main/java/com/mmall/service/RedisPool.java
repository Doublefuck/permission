package com.mmall.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * 封装redis的操作
 * Created by Administrator on 2018/3/26 0026.
 */
@Service("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name="shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /**
     * 获取连接redis客户端的实例对象
     * 由于ShardedJedisPool是被spring管理的单例类，
     * 因此此处只能获取到一个redis
     * @return
     */
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    /**
     * 关闭redis连接
     * @param shardedJedis
     */
    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception", e);
        }
    }

}
