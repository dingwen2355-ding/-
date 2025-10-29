package com.jslc.common.utils;

import org.springblade.core.redis.cache.BladeRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName SquirrelRedis
 * @Description redis工具类
 * @Author zhengyongbiao
 * @Date 2020/7/12
 * @Version 3.6.0
 * @Since JDK 1.8
 **/
@Component
public class SquirrelRedis {
    private static final Long SUCCESS = 1L;
    @Autowired
    private BladeRedis bladeRedis;
    //lua上锁脚本
    private static final String LOCK_LUA = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 'true' else return 'false' end";
    //lua解锁脚本
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) end return 'true' ";
    public static final int DEFAULT_SECOND_LEN = 60; // 10 s

    private RedisSerializer<String> argsSerializer  = new StringRedisSerializer();
    private RedisSerializer<String> resultSerializer = new StringRedisSerializer();

    /**
     * @Date 2020/7/11 21:52
     * @Author zhengyongbiao
     * @Description
     * @Param [lockKey 锁住的key, lockExpireMils 锁住的时长。如果超时未解锁，视为加锁线程死亡，其他线程可夺取锁]
     * @Return boolean
     */
    public boolean lock(String lockKey, long lockExpireMils) {
        return (Boolean)  bladeRedis.getRedisTemplate().execute((RedisCallback) connection -> {
            long nowTime = System.currentTimeMillis();
            Boolean acquire = connection.setNX(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
            if (acquire) {
                return Boolean.TRUE;
            } else {
                byte[] value = connection.get(lockKey.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long oldTime = Long.parseLong(new String(value));
                    if (oldTime < nowTime) {
                        //connection.getSet：返回这个key的旧值并设置新值。
                        byte[] oldValue = connection.getSet(lockKey.getBytes(), String.valueOf(nowTime + lockExpireMils + 1).getBytes());
                        //当key不存时会返回空，表示key不存在或者已在管道中使用
                        return oldValue == null ? false : Long.parseLong(new String(oldValue)) < nowTime;
                    }
                }
            }
            return Boolean.FALSE;
        });
    }

    /**
     * @Date 2020/7/11 8:52
     * @Author zhengyongbiao
     * @Description
     * @Param [lockKey锁住的key, val key值]
     * @Return boolean
     */
    public boolean lock(String lockKey, String val) {
        return this.lock(lockKey, val, DEFAULT_SECOND_LEN);
    }

    /**
     * @Date 2020/7/11 8:53
     * @Author zhengyongbiao
     * @Description
     * @Param [lockKey锁住的key, val key值，确保唯一, second 过期时间，防止死锁]
     * @Return boolean
     */
    public boolean lock(String lockKey, String val, int second) {
        RedisScript lockRedisScript = RedisScript.of(LOCK_LUA, String.class);
        List<String> keys = Collections.singletonList(lockKey);
        String flag = bladeRedis.getRedisTemplate().execute(lockRedisScript, argsSerializer, resultSerializer, keys, val, String.valueOf(second));
        return Boolean.valueOf(flag);
    }

    /**
     * @Date 2020/7/11 8:53
     * @Author zhengyongbiao
     * @Description 通过key和val值解锁，只有满足key值和val值相等才能解锁，防止被其他线程并发解锁
     * @Param [lockKey 被锁的key, val key值]
     * @Return void
     */
    public void unlock(String lockKey, String val) {
        RedisScript unLockRedisScript = RedisScript.of(UNLOCK_LUA, String.class);
        List<String> keys = Collections.singletonList(lockKey);
        bladeRedis.getRedisTemplate().execute(unLockRedisScript, argsSerializer, resultSerializer, keys, val);
    }

}
