package com.boot.rabbitmq;

import com.boot.redis.EnableRedisClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @date 2020/8/25 15:59
 * @description 整合rabbitmq，采用的是amqp 协议 。 [delegate=amqp://admin@192.168.1.218:5672/, localPort= 58845]
 * 1:fanout 模式就是广播模式,消息来了所有 consumer 都能接收到消息
 * 2:Direct 模式就是指定队列模式， 消息来了，只发给指定的 Queue, 其他Queue 都收不到。
 * 3:topic  模式，可以多个主题进行组合
 * 4:header 模式，头部匹配
 * producer 底层步骤：
 * 1：ConnectionFactory 创建连接工厂
 * 2：由 connectionFactory 创建 connection
 * 3: connection 创建 channel
 * 4: channel 设置队列模式，队列标签
 * 5：channel 发布消息
 * consumer 底层步骤：
 * 1：ConnectionFactory 创建连接工厂
 * 2：由 connectionFactory 创建 connection
 * 3: connection 创建 channel
 * 4: channel 设置队列模式，队列标签
 * 5：channel 监听队列中的消息并消费
 * tips:
 * 1:底层的connection、channel 都用的 代理模式（proxy）
 * 2:Exchange 的代表交换机，需要设置交换机名称、交换机类型
 * 3:Queue 代表队列
 **/
@SpringBootApplication
@EnableRedisClient
public class BootRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootRabbitmqApplication.class, args);
    }

}
