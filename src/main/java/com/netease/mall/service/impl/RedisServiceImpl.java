package com.netease.mall.service.impl;

import com.netease.mall.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author : francis
 * @description :
 * @time : 2018/3/28,13:03
 * @update :
 */
@Service
public class RedisServiceImpl implements RedisService, InitializingBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379");
    }

    @Override
    public Long save(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hset(key, field, value);
        }catch (Exception e){
            LOGGER.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public String get(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hget(key, field);
        }catch (Exception e){
            LOGGER.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public boolean isMember(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hexists(key, field);
        }catch (Exception e){
            LOGGER.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    @Override
    public Set<String> keys(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        }catch (Exception e){
            LOGGER.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.del(key);
        }catch (Exception e){
            LOGGER.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        JedisPool pool = new JedisPool("redis://localhost:6379");
    }
}
