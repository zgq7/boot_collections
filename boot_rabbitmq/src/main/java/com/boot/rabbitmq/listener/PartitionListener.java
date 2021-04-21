package com.boot.rabbitmq.listener;

import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.stream.RabbitStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020/9/17 18:35
 * @description
 **/
@Component
@EnableBinding(RabbitStream.class)
public class PartitionListener {

    private static final Logger logger = LoggerFactory.getLogger(PartitionListener.class);

    @StreamListener(MqConstants.PARTITION_EXCHANGE)
    public void receiveMsgAutoCommit(@Payload String payload) {
        logger.info("consumer:{}", payload);
    }

}
