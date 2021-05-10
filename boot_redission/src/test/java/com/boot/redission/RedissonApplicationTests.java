package com.boot.redission;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        //final RedissonFairLock redissonLock = (RedissonFairLock) redissonClient.getFairLock(redissonLcokName);
        final RedissonLock redissonLock = (RedissonLock) redissonClient.getLock(redissonLcokName);

        CountDownLatch latch = new CountDownLatch(cap);
        for (int i = 0; i < cap; i++) {
            EXECUTOR.execute(() -> {
                try {
                    redissonLock.lock(100, TimeUnit.SECONDS);
                    //redissonLock.lock();
                    logger.info("{} get locked...", Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception ignore) {

                } finally {
                    if (redissonLock.isLocked())
                        redissonLock.unlock();
                    logger.info("{} unlocking...", Thread.currentThread().getName());
                    latch.countDown();
                }
            });
        }

        latch.await();
        EXECUTOR.shutdown();
    }

    @Test
    void contextLoad2s() {
        final String redissonLcokName = "redis-lock";
        final RedissonLock redissonLock = (RedissonLock) redissonClient.getLock(redissonLcokName);

        try {
            redissonLock.lock(100, TimeUnit.SECONDS);
        } catch (Exception ignore) {

        } finally {
            if (redissonLock.isLocked())
                redissonLock.unlock();
        }
    }


    @Test
    void helloDog() {
        HashedWheelTimer timer = new HashedWheelTimer();

        timer.newTimeout((timeTask) -> {
            logger.info("hello dog");
        }, 1, TimeUnit.SECONDS);

        timer.start();
    }


}
