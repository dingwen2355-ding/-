package com.grandlynn.spa.catalogue.config;

import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisTemplateConfig {
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
      redisTemplate.setConnectionFactory(redisConnectionFactory);
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
      redisTemplate.afterPropertiesSet();
      return redisTemplate;
   }

   @Bean
   public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
      RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1L)).serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())).computePrefixWith((name) -> {
         return name + ":";
      });
      RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).cacheDefaults(defaultCacheConfiguration);
      return builder.build();
   }
}
