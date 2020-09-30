package com.boot.rabbitmq.atm.dlx;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:31
 * @description 死信队列消费者
 * 消费者限制预读处理：https://www.rabbitmq.com/consumer-prefetch.html
 **/
public class DlxConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DlxConsumer.class);

    private static final Map<String, Object> headerMap = new HashMap<>();
    // 死信队列参数
    private static final String DLX_KEY = "x-dead-letter-exchange";

    private static final String DLX_QUEUE = "dlx.queue";
    private static final String DLX_TEST_QUEUE = "dlx.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());
        // 2:由连接工厂创建连接实例（单线程消费的连接）
        Connection connection = connectionFactory.newConnection(Executors.newSingleThreadExecutor(), "dlx-connection");
        // 3:连接实例创建管道
        Channel channel = connection.createChannel();
        // 4:声明死信队列
        headerMap.put(DLX_KEY, DlxProducer.EXCHANGE_NAME);

        // 5:绑定正常队列,正常队列需指定任务的死信队列
        channel.exchangeDeclare(DlxProducer.EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());
        channel.queueDeclare(DLX_TEST_QUEUE, true, false, false, headerMap);
        channel.queueBind(DLX_TEST_QUEUE, DlxProducer.EXCHANGE_NAME, DlxProducer.ROUTING_KEY);

        // 6:绑定死信队列
        channel.exchangeDeclare(DlxProducer.EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());
        channel.queueDeclare(DLX_QUEUE, true, false, false, null);
        channel.queueBind(DLX_QUEUE, DlxProducer.EXCHANGE_NAME, DlxProducer.ROUTING_KEY);

        // 7:创建消费者实例
        Consumer consumer = newConsumer(channel);
        // 8:设置消费者的消息预取数量（spring RabbitTemplate 默认）
        channel.basicQos(1);
        // 9:消息进行手动确认，确认之后才会监听队列并消费消息
        channel.basicConsume(DLX_TEST_QUEUE, true, consumer);
        channel.basicConsume(DLX_QUEUE, true, consumer);

        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
        }

    }

    private static Consumer newConsumer(Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                logger.info("queueName:{} routingKey:{} -> consume msg : {}", envelope.getExchange(), envelope.getRoutingKey(), new String(body, StandardCharsets.UTF_8));
            }
        };

    }

}
