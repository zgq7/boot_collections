package com.boot.rabbitmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.producer.MqDemoProducer;
import com.boot.rabbitmq.stream.MqDemoStream;
import com.boot.redis.RedisComUtil;
import com.boot.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaonanzhou
 * @date 2020/8/25 11:06
 * @description
 **/
@Component
@EnableBinding(MqDemoStream.class)
public class MqDemoListener {

    private static final Logger logger = LoggerFactory.getLogger(MqDemoListener.class);

    private AtomicInteger tg = new AtomicInteger(1);

    private final RedisUtil redisUtil;
    private final RedisComUtil redisComUtil;
    private final MqDemoProducer mqDemoProducer;

    public MqDemoListener(RedisUtil redisUtil, RedisComUtil redisComUtil, MqDemoProducer mqDemoProducer) {
        this.redisUtil = redisUtil;
        this.redisComUtil = redisComUtil;
        this.mqDemoProducer = mqDemoProducer;
    }

    /**
     * 普通接收消息
     * 考虑同ID 不同version 的数据按顺序消费
     **/
    @StreamListener(MqConstants.MQ_DEMO_INPUT)
    public void receiveMsgAutoCommit(@Payload String payload,
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        waiting(50);

        MqModel model = JSONObject.parseObject(payload, MqModel.class);
        String redisKey = model.parseRedisKey();

        List<Object> objectList = new ArrayList<>(redisUtil.zRange(redisKey, 0, 0));
        if (objectList.size() > 0) {
            Object member = objectList.get(0);
            String redisMember = (String) member;
            if (redisMember.equals(model.parseRedisMember())) {
                // 消息能正常消费
                consumeMsg(model);
                //redisComUtil.zPopMax(redisKey);
                redisUtil.zRem(redisKey, member);
            }  else {
                // fixme 消息打回
                // 1：消息version 比较大
                // 2：redis中不存在该消息的序号（消息过期）
                // 3：无法确定消息的消费顺序（可能该消息后面仍然存在version比较小的）
                mqDemoProducer.send(model);
            }
        } else {
            // 消息过期，重新消费
            mqDemoProducer.send(model);
        }
    }

    private void consumeMsg(MqModel model) {
        long v = model.getVersion();
        logger.info("{}", model.parseRedisMember(v));
    }

    private void waiting(int timeout) {
        if (tg.get() == 1) {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tg.incrementAndGet();
        }
    }

}
