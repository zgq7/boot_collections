package com.boot.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.boot.rabbitmq.constance.MqDemoConst;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.stream.MqDemoStream;
import com.boot.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020/8/25 10:42
 * @description @EnableBinding 会把目标类注入成bean
 **/
@Component
@EnableBinding(MqDemoStream.class)
public class MqDemoProducer {

    private static final Logger logger = LoggerFactory.getLogger(MqDemoProducer.class);

    private AtomicInteger ati = new AtomicInteger(1);
    private AtomicInteger atm = new AtomicInteger(2);

    private final RedisUtil redisUtil;

    private final MqDemoStream mqDemoStream;

    public MqDemoProducer(RedisUtil redisUtil, MqDemoStream mqDemoStream) {
        this.redisUtil = redisUtil;
        this.mqDemoStream = mqDemoStream;
    }

    public void sendMsg() {
        ThreadPoolExecutor executor = MqDemoConst.EXECUTOR;
        for (; ati.get() < atm.get(); ati.incrementAndGet()) {
            final int ti = ati.get();
            final int rd = new Random().nextInt(5) * 2;
            for (int j = 1; j <= rd; j++) {
                final int name = j;
                executor.execute(() -> {
                    Thread.currentThread().setName("producer-thread-" + name);
                    send(ti, name);
                });
            }
        }
        ati.set(1);
    }

    private void send(final int i, final int j) {
        MqModel model = new MqModel(i, getVersionForId(i), j);
        try {
            TimeUnit.MILLISECONDS.sleep(100 - j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("produce msg ->{}", JSON.toJSONString(model));
        send(model);
    }

    public void send(MqModel model) {
        redisUtil.zAdd(model.parseRedisKey(), model.parseRedisMember(), model.getVersion());
        mqDemoStream.streamOutput()
                .send(MessageBuilder.withPayload(model).build());
    }

    private long getVersionForId(final int id) {
        synchronized (this) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return System.currentTimeMillis();
        }
    }

}
