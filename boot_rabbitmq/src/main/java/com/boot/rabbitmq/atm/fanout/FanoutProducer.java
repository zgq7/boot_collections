package com.boot.rabbitmq.atm.fanout;

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
 * @description fanout 类似于广播形式，一条消息所有的消费者都会收到
 * https://www.jianshu.com/p/8414cd70bef1
 **/
public class FanoutProducer {

    // 交换机名称
    protected static final String EXCHANGE_NAME = "ex_fanout";
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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType());

        // 5:再由管道发布消息
        for (int i = 0; i < 1000; i++) {
            String msg = BuiltinExchangeType.FANOUT.getType() + ": " + i + " : " + UUID.randomUUID();
            // 发布的消息时必须有消费者（需提前创建队列）存在，否则消息无法路由，后面启动的消费者无法接受到消息（消息丢失）
            // 版本3.5.0，版本 3.6.0 之上可能此现象得到改善
            // stack_flow : https://stackoverflow.com/questions/38988225/is-it-possible-to-get-unrouted-messages-in-amqp
            // github issues : https://github.com/thecederick/rabbitmq-arguments-to-headers-exchange/issues/1
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
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
