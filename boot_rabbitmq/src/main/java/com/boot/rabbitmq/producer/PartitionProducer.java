package com.boot.rabbitmq.producer;

import com.alibaba.fastjson.JSON;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.stream.RabbitStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020/9/17 18:29
 * @description
 **/
@Component
@EnableBinding(RabbitStream.class)
public class PartitionProducer {

    private static final Logger logger = LoggerFactory.getLogger(PartitionProducer.class);

    public PartitionProducer(RabbitStream rabbitStream) {
        this.rabbitStream = rabbitStream;
    }

    private final RabbitStream rabbitStream;

    public void sendMsg(MqModel model) {
        logger.info("producer:{}", JSON.toJSONString(model));

        rabbitStream.partitionProducer()
                .send(MessageBuilder.withPayload(model)
                        .build()
                );
    }

}
