package com.boot.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.listener.CommitListener;
import com.boot.rabbitmq.stream.RabbitStream;
import com.boot.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    public CommitProducer(RabbitStream rabbitStream) {
        this.rabbitStream = rabbitStream;
    }

    public void sendMsg(MqModel model) {
        rabbitStream.commitProducer()
                .send(MessageBuilder.withPayload(model).build());
        logger.info("producer:{}", JSON.toJSONString(model));
    }

}
