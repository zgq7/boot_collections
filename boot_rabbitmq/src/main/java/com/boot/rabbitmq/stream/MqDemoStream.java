package com.boot.rabbitmq.stream;

import com.boot.rabbitmq.constance.MqConstants;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author liaonanzhou
 * @date 2020/8/25 10:40
 * @description
 **/
public interface MqDemoStream {

    /**
     * 消息流入
     **/
    @Input(MqConstants.MQ_DEMO_INPUT)
    SubscribableChannel streamInput();

    /**
     * 消息流出
     **/
    @Output(MqConstants.MQ_DEMO_OUTPUT)
    MessageChannel streamOutput();

    /**
     * 消息流入
     **/
    @Input(MqConstants.MQ_PARTITION_INPUT)
    SubscribableChannel mqPartitionInput();

    /**
     * 消息流出
     **/
    @Output(MqConstants.MQ_PARTITION_OUTPUT)
    MessageChannel mqPartitionOutput();


}
