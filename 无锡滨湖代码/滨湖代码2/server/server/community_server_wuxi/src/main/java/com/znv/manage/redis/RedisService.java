package com.znv.manage.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 用于对外提供redis服务，最好不要依赖项目中的类
 */
@Service
@Slf4j
public class RedisService {

    private StringRedisTemplate template;
    ValueOperations<String, String> valueOperations;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.template = stringRedisTemplate;
        valueOperations = template.opsForValue();
    }

    public Boolean hasKey(String key) {
        return template.hasKey("ThirdToken:" + key);
    }

    public void expireThirdToken(String key) {
        template.expire("ThirdToken:" + key, 120, TimeUnit.MINUTES);
    }
}
