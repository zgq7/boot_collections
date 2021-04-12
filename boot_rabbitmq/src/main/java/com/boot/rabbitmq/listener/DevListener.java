package com.boot.rabbitmq.listener;

import com.boot.rabbitmq.stream.RabbitStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020/8/25 11:06
 * @description
 **/
@Component
@EnableBinding(RabbitStream.class)
public class DevListener {

    private static final Logger logger = LoggerFactory.getLogger(DevListener.class);

    /**
     * 普通接收消息
     * 考虑同ID 不同version 的数据按顺序消费
     **/
    //@StreamListener(MQConstants.DEV_EXCHANGE)
    public void receiveMsgAutoCommit(@Payload String payload) {
        logger.info("consumer:{}", payload);
    }

}
