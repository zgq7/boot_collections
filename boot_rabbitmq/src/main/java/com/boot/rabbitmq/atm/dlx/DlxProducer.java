package com.boot.rabbitmq.atm.dlx;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.boot.rabbitmq.atm.SettingHelper.init;

/**
 * @author liaonanzhou
 * @date 2020/9/21 10:15
 * @description 为什么会有死信队列 ？
 * 1：消息超时
 * 2：队列长度达到极限
 * 3：消息被拒绝消费，并不再重进队列 reQueue = false
 **/
public class DlxProducer {

    private static final Logger logger = LoggerFactory.getLogger(DlxProducer.class);

    // 交换机名称
    protected static final String EXCHANGE_NAME_1 = "dlx_exchange_1";
    protected static final String EXCHANGE_NAME_2 = "dlx_exchange_2";
    // 作用是告诉交换机消息要推往哪个队列
    protected static final String ROUTING_KEY = "DLX.RK";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1:创建连接工厂
        ConnectionFactory connectionFactory = init(new ConnectionFactory());
        // 2:由连接工厂创建连接实例
        Connection connection = connectionFactory.newConnection();
        // 3:连接实例创建管道
        Channel channel = connection.createChannel();

        // 创建 properties
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding(StandardCharsets.UTF_8.displayName())  //消息编码
                .expiration("10000")   //消息过期时间 10秒
                .build();

        // 5:再由管道发布消息
        for (int i = 0; i < 100; i++) {
            String msg = BuiltinExchangeType.TOPIC.getType() + " : " + UUID.randomUUID();
            // 生产者只需要在发布消息的时候声明交换机和routingKey即可，不需要提前声明绑定队列和交换机
            channel.basicPublish(EXCHANGE_NAME_1, ROUTING_KEY, true, properties, msg.getBytes());
            logger.info("produce msg : {}", msg);
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
