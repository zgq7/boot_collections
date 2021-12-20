package com.boot.redission.expire;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2021/12/16 17:04
 * @description
 **/
@Component
public class RedissonKeyExpireListener implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(RedissonKeyExpireListener.class);

    private final RedissonClient redissonClient;
    private final RMapCache<String, String> rMapCache;

    public RedissonKeyExpireListener(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.rMapCache = this.redissonClient.getMapCache("redisson-expire-name");
        int expireListenerID = rMapCache.addListener(new EntryExpiredListener<String, String>() {
            @Override
            public void onExpired(EntryEvent<String, String> event) {
                logger.info("过期key = {}，过期old value = {}，过期value = {}", event.getKey(), event.getOldValue(), event.getValue());
            }
        });
        logger.info("listener id = {}", expireListenerID);
    }

    public void addKeyListener(String key, String value) {
        rMapCache.put(key, value, 10, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //rMapCache.put("redisson-expire1", "value", 10, TimeUnit.SECONDS);
        //rMapCache.put("redisson-expire2", "value", 20, TimeUnit.SECONDS);
        //rMapCache.put("redisson-expire3", "value", 30, TimeUnit.SECONDS);
    }
}
