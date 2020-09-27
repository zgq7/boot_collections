package com.boot.rabbitmq.atm.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:15
 * @description https://www.jianshu.com/p/8414cd70bef1
 * @apiNote RK.* 作为 routingKey 可以匹配 RK.A、RK.B、RK.C、RK.Bbb 等多个routingKey
 **/
public class TopicProducer {

    // 交换机名称
    protected static final String EXCHANGE_NAME = "ex_topic";
    // 作用是告诉交换机消息要推往哪个队列
    protected static final String ROUTING_KEY_A = "RK.A";
    protected static final String ROUTING_KEY_B = "RK.B";
    protected static final String ROUTING_KEY_X = "RK.*";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());

        // 2:由连接工厂创建连接实例
        Connection connection = connectionFactory.newConnection();

        // 3:连接实例创建管道
        Channel channel = connection.createChannel();

        // 4:在管道上声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());

        // 5:再由管道发布消息
        for (int i = 0; i < 1000; i++) {
            String msg = BuiltinExchangeType.TOPIC.getType() + ": " + i + " : " + UUID.randomUUID();
            channel.basicPublish(EXCHANGE_NAME, (i + 1) % 2 == 0 ? ROUTING_KEY_A : ROUTING_KEY_B, null, msg.getBytes());
            System.out.println("produce msg :" + msg);

        }

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
