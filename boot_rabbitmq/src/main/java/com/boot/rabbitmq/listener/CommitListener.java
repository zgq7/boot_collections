package com.boot.rabbitmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.stream.RabbitStream;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2020/9/1 11:13
 * @description
 **/
@Component
@EnableBinding(RabbitStream.class)
public class CommitListener {

    private static final Logger logger = LoggerFactory.getLogger(CommitListener.class);

    /**
     * 设置手动提交消息确认
     **/
    @StreamListener(MqConstants.COMMIT_EXCHANGE)
    public void receiveMsg(@Payload String payload,
                           @Header(AmqpHeaders.CHANNEL) Channel channel,
                           @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag ) {
        try {
            JSONObject object = JSONObject.parseObject(payload);
            logger.info("commit listener -> payload : {},deliveryTag : {}", payload, deliveryTag);
            if (object != null) {
                // 消息OK，将从队列中移除该消息
                TimeUnit.MILLISECONDS.sleep(100);
                channel.basicAck(deliveryTag, false);
            } else {
                // 消息ERROR，将重复消费该消息
                channel.basicNack(deliveryTag, false, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(MqConstants.COMMIT_EXCHANGE_PRODUCER_CONFIRM)
    public void receiveConfirm(@Payload String payload,
                               @Header(AmqpHeaders.TIMESTAMP) long timestamp) {
        logger.info("confirm-> {},timestamp -> {}", payload, timestamp);
    }

}
