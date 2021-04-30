package com.boot.redission;

import jodd.util.concurrent.ThreadFactoryBuilder;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonFairLock;
import org.redisson.RedissonLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.RedisCommand;
import org.redisson.client.protocol.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.*;

@SpringBootTest
class RedissonApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(RedissonApplicationTests.class);
    @Resource
    private RedissonClient redissonClient;
    private final static int cap = 10;
    private final static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(cap, cap, 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    @Test
    void contextLoads() throws InterruptedException {
        final String redissonLcokName = "redis-lock";
        final RedissonFairLock redissonLock = (RedissonFairLock) redissonClient.getFairLock(redissonLcokName);

        CountDownLatch latch = new CountDownLatch(cap);
        for (int i = 0; i < cap; i++) {
            EXECUTOR.execute(() -> {
                try {
                    redissonLock.lock(10, TimeUnit.SECONDS);
                    //redissonLock.lock();
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception ignore) {

                } finally {
                    logger.info("{} unlocking...", Thread.currentThread().getName());
                    redissonLock.unlock();
                    latch.countDown();
                }
            });
        }

        latch.await();
        EXECUTOR.shutdown();
    }

}
