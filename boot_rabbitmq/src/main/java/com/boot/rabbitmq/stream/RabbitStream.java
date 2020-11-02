package com.boot.rabbitmq.stream;

import com.boot.rabbitmq.constance.MQConstants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author liaonanzhou
 * @date 2020/8/25 10:40
 * @description
 **/
public interface RabbitStream {

    /**
     * 消息流入（消费）
     **/
    @Input(MQConstants.DEV_EXCHANGE)
    SubscribableChannel devConsumer();

    /**
     * 消息流出（生产）
     **/
    @Output(MQConstants.DEV_EXCHANGE)
    MessageChannel devProducer();

    /**
     * 消息流入（消费）
     **/
    @Input(MQConstants.PARTITION_EXCHANGE)
    SubscribableChannel partitionConsumer();

    /**
     * 消息流出（生产）
     **/
    @Output(MQConstants.PARTITION_EXCHANGE)
    MessageChannel partitionProducer();

    /**
     * 消息流入（消费）
     **/
    @Input(MQConstants.COMMIT_EXCHANGE)
    SubscribableChannel commitConsumer();

    /**
     * 消息流出（生产）
     **/
    @Output(MQConstants.COMMIT_EXCHANGE)
    MessageChannel commitProducer();


}
