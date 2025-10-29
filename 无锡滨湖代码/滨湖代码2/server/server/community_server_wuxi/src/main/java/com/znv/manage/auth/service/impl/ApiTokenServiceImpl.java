package com.znv.manage.auth.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.znv.manage.auth.config.ApiTokenConfig;
import com.znv.manage.auth.service.ApiTokenService;
import com.znv.manage.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: yf
 * @Date: 2020/7/1
 * @Description:
 */
@Service
@Slf4j
public class ApiTokenServiceImpl implements ApiTokenService {

    // token存储介质：session(session)、本地缓存(local-cache)、redis(redis)等
    private static final String TOKEN_STORETYPE_SESSION = "session";
    private static final String TOKEN_STORETYPE_LOCALCACHE = "local-cache";

    @Autowired
    ApiTokenConfig apiTokenConfig;

    @Value("${auth.apitokencheck.expiration}")
    private  String expiration;

    // 利用guava通过CacheBuilder构建一个缓存实例
    private static Cache<String, String> tokenCache;

    @PostConstruct
    public void init() {
        tokenCache = CacheBuilder.newBuilder()
                .maximumSize(5000) // 设置缓存的最大容量
//            .expireAfterWrite(30, TimeUnit.MINUTES) // 设置缓存在写入多久后后失效
                .expireAfterWrite(Long.valueOf(expiration), TimeUnit.SECONDS) // 设置缓存在写入多久后后失效
                .concurrencyLevel(100) // 设置并发级别
                .recordStats() // 开启缓存统计
                .build();
    }


    @Override
    public void storeToken(String token, HttpSession session) {
        if (TOKEN_STORETYPE_SESSION.equals(apiTokenConfig.getStoreType())) {
            session.setAttribute(token, DateUtil.getStringDate());
            session.setMaxInactiveInterval(apiTokenConfig.getExpiration());
        } else if (TOKEN_STORETYPE_LOCALCACHE.equals(apiTokenConfig.getStoreType())) {
            tokenCache.put(token, DateUtil.getStringDate());
        }
    }

    @Override
    public boolean checkToken(String token, HttpSession session) {
        if (TOKEN_STORETYPE_SESSION.equals(apiTokenConfig.getStoreType())) {
            return session.getAttribute(token) != null;
        } else if (TOKEN_STORETYPE_LOCALCACHE.equals(apiTokenConfig.getStoreType())) {
            return tokenCache.getIfPresent(token) != null;
        }

        return false;
    }
}
