package com.boot.rabbitmq.listener;

import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.stream.MqDemoStream;
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
@EnableBinding(MqDemoStream.class)
public class MqPartitionListener {

    private static final Logger logger = LoggerFactory.getLogger(MqPartitionListener.class);

    @StreamListener(MqConstants.MQ_PARTITION_INPUT)
    public void receiveMsgAutoCommit(@Payload String payload) {
        logger.info(payload);
    }

}
