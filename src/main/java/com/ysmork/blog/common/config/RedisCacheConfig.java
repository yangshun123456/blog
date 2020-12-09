package com.ysmork.blog.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description:
 * @date 2020/10/26 22:22
 */
@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<> ();
        // 缓存永不过期
        cacheConfigurations.put("province", this.redisCacheConfiguration(Duration.ZERO));
        cacheConfigurations.put("city", this.redisCacheConfiguration(Duration.ZERO));
        cacheConfigurations.put("district", this.redisCacheConfiguration(Duration.ZERO));
        RedisCacheManager redisCacheManager = redisCacheManagerBuilder.withInitialCacheConfigurations(cacheConfigurations).build();
        return redisCacheManager;
    }

    private RedisCacheConfiguration redisCacheConfiguration(Duration duration){
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericFastJsonRedisSerializer)).entryTtl(duration);
        return configuration;
    }

}
