package com.mmall.service.impl;

import com.google.common.base.Joiner;
import com.mmall.bean.CacheKeyConstants;
import com.mmall.service.RedisPool;
import com.mmall.service.ISysCacheService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * 系统缓存数据管理
 * Created by Administrator on 2018/3/26 0026.
 */
@Slf4j
public class SysCacheService implements ISysCacheService {

    @Resource
    private RedisPool redisPool;

    public void saveCache(String toSaveValue, int timeout, CacheKeyConstants prefix) {
        saveCache(toSaveValue, timeout, prefix, null);
    }

    public void saveCache(String toSaveValue, int timeout, CacheKeyConstants prefix, String... keys) {
        if (toSaveValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeout, toSaveValue);
        } catch (Exception e) {
            log.error("save cache error, prefix:{}, keys:{}", prefix, keys, e);
        } finally {
            shardedJedis.close();
        }
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        } catch (Exception e) {
            log.error("get from cache exception, prefix:{}, keys:{}", prefix, keys, e);
            return null;
        } finally {
            shardedJedis.close();
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }

}
