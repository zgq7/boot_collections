package com.boot.rabbitmq.atm.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;
import static com.boot.rabbitmq.atm.fanout.FanoutProducer.EXCHANGE_NAME;
import static com.boot.rabbitmq.atm.fanout.FanoutProducer.ROUTING_KEY;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:31
 * @description
 **/
public class FanoutConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());

        // 2:由连接工厂创建连接实例
        Connection connection = connectionFactory.newConnection();

        // 3:连接实例创建管道
        Channel channel = connection.createChannel();

        // 4:在管道上声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType());

        // 5:获取队列
        String queueName = channel.queueDeclare().getQueue();  // 随机获取QueueName
        //String queueName = channel.queueDeclare("test", false, true, true, null).getQueue();

        // 6:队列和交换机进行绑定
        channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

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
        }finally {
            channel.close();
            connection.close();
        }

    }


}
