package com.boot.rabbitmq.atm.template;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2022/7/26 15:55
 * @description
 **/
@Component
public class MessageSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    public void sendAsync(String exchangeName, String routingKey, Object message) {
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 监听消息是否到达 queue
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        // 监听消息是否到达 exchange
    }
}
