package com.example.telegram_bot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName telegram_bot-RedisDao
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2023年5月23日17:05 - 周二
 * @Describe
 */
@Repository
public class RedisDao {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置自定义小时的key
     *
     * @param key   key
     * @param value value
     * @param hour  小时
     */
    public void setHour(String key, Object value, Integer hour) {
        redisTemplate.opsForValue().set(key, value, hour, TimeUnit.HOURS);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
