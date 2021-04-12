package com.boot.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.stream.RabbitStream;
import com.boot.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020/8/25 10:42
 * @description @EnableBinding 会把目标类注入成bean
 **/
@Component
@EnableBinding(RabbitStream.class)
public class CommitProducer {

    private static final Logger logger = LoggerFactory.getLogger(CommitProducer.class);

    private final RabbitStream rabbitStream;

    public CommitProducer(RedisUtil redisUtil, RabbitStream rabbitStream) {
        this.rabbitStream = rabbitStream;
    }

    public void sendMsg(MqModel model) {
        logger.info("producer:{}", JSON.toJSONString(model));

        rabbitStream.commitProducer()
                .send(MessageBuilder.withPayload(model).build());
    }

}
