package com.boot.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.boot.rabbitmq.constance.MQModel;
import com.boot.rabbitmq.stream.RabbitStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author zgq7
 * @date 2020/8/25 10:42
 * @description @EnableBinding 会把目标类注入成bean
 **/
@Component
@EnableBinding(RabbitStream.class)
public class DevProducer {

    private static final Logger logger = LoggerFactory.getLogger(DevProducer.class);

    private final RabbitStream rabbitStream;

    public DevProducer(RabbitStream rabbitStream) {
        this.rabbitStream = rabbitStream;
    }

    public void sendMsg(MQModel model) {
        logger.info("producer:{}", JSON.toJSONString(model));

        rabbitStream.devProducer()
                .send(MessageBuilder.withPayload(model)
                        .setHeader("mid", model.getMid())
                        .build()
                );
    }

}
