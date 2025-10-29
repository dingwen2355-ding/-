package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SnowflakeGenerator {
    private static long serverId;
    @Value("${server.id:2}")
    public void setServerId(long serverId){
        SnowflakeGenerator.serverId = serverId;
    }

    private static long lastGeneratorId = 0;

    /** 开始时间截 (2015-01-01) */
    private static final long twepoch = 1420041600000L;
    private static long sequence = 0L;

    /* 41为时间戳，10位serverid，12位seq*/
    public static synchronized long generatorId(){
        long timestamp = System.currentTimeMillis();
        lastGeneratorId = ((timestamp - twepoch) << (10 + 12))
                | (serverId << 12)
                | (sequence++);
        log.info("Generator id : " + lastGeneratorId);
        return lastGeneratorId;
    }

    public static long getIdTimestamp(long id){
        return (id>>(10 + 12)) + twepoch;
    }
    public long getServerId(long id){return (id >> 12) & 0x3FF;}
    public long getSeq(long id){return id & 0x0FFF;}

    public long getLastIdTimestamp(){
        return getIdTimestamp(lastGeneratorId);
    }
}
