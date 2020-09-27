package com.boot.rabbitmq.atm.header;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:31
 * @description
 **/
public class HeaderConsumer {

    private static final Map<String, Object> headerMap = new HashMap<>();
    // 头部匹配规则
    private static final String MATCH_KEY = "x-match";
    // 头部匹配类型
    private static final String ALL = "all";
    private static final String ANY = "any";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());

        // 2:由连接工厂创建连接实例
        Connection connection = connectionFactory.newConnection();

        // 3:连接实例创建管道
        Channel channel = connection.createChannel();

        // 4:在管道上声明一个交换机
        channel.exchangeDeclare(HeaderProducer.EXCHANGE_NAME, BuiltinExchangeType.HEADERS.getType());

        // 5:获取队列
        String queueName = channel.queueDeclare().getQueue();  // 随机获取QueueName
        //String queueName = channel.queueDeclare("topic_queue", false, true, true, null).getQueue();  //自定义QueueName

        // 6:队列和交换机进行绑定
        matchAll(true);
        headerMap.put("userId", "1");
        headerMap.put("ggId", "1");
        channel.queueBind(queueName, HeaderProducer.EXCHANGE_NAME, HeaderProducer.ROUTING_KEY, headerMap);

        // 7:创建消费者实例
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("consume msg : " + new String(body, StandardCharsets.UTF_8));
            }
        };

        // 8:消息进行手动确认
        channel.basicConsume(queueName, true, consumer);

        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
        }

    }

    private static void matchAll(boolean match) {
        if (match) {
            System.out.println("header模式匹配规则:" + ALL);
            headerMap.put(MATCH_KEY, ALL);
        } else {
            System.out.println("header模式匹配规则:" + ANY);
            headerMap.put(MATCH_KEY, ANY);
        }
    }


}
