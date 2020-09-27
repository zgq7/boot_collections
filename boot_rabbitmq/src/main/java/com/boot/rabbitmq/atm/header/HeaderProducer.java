package com.boot.rabbitmq.atm.header;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:15
 * @description
 **/
public class HeaderProducer {

    // 交换机名称
    protected static final String EXCHANGE_NAME = "ex_header";
    // 作用是告诉交换机消息要推往哪个队列
    protected static final String ROUTING_KEY = "RK";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());

        // 2:由连接工厂创建连接实例
        Connection connection = connectionFactory.newConnection();

        // 3:连接实例创建管道
        Channel channel = connection.createChannel();

        // 4:在管道上声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS.getType());

        // 创建 headers 模式的 properties
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("userId", "2");
        headerMap.put("ggId", "1");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().headers(headerMap).build();

        // 5:再由管道发布消息
        String msg = BuiltinExchangeType.HEADERS.getType() + " : " + UUID.randomUUID();
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, msg.getBytes());
        System.out.println("produce msg :" + msg);

        try {
            TimeUnit.SECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
        }
    }

}
