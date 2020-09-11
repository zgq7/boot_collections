package com.boot.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaonanzhou
 * @date 2020/9/3 15:37
 * @description
 **/
@AutoConfigureAfter(RedisProperties.class)
@Configuration
public class RedisComConfig {

    private final static Logger logger = LoggerFactory.getLogger(RedisComConfig.class);

    /**
     * 客户端配置
     **/
    @Bean
    public RedisClient redisClient(RedisProperties redisProperties) {
        RedisURI redisURI = RedisURI.builder()
                .withHost(redisProperties.getHost())
                .withPort(redisProperties.getPort())
                .withPassword(redisProperties.getPassword())
                .withDatabase(redisProperties.getDatabase())
                .withTimeout(redisProperties.getTimeout())
                .build();
        logger.debug("redis client bean would be create");
        return RedisClient.create(redisURI);
    }

    /**
     * 连接池配置
     **/
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig(RedisProperties redisProperties) {
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        if (pool != null) {
            genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
            genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
            genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
            genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
            genericObjectPoolConfig.setJmxEnabled(false);
            if (pool.getTimeBetweenEvictionRuns() != null) {
                genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
            }
        }

        return genericObjectPoolConfig;
    }

    @Bean
    public RedisComUtil redisComUtil(RedisClient redisClient,
                                     GenericObjectPoolConfig genericObjectPoolConfig) {
        return new RedisComUtil(ConnectionPoolSupport.createGenericObjectPool(redisClient::connect, genericObjectPoolConfig));
    }

}
