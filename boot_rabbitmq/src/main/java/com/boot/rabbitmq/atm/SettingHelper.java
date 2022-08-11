package com.boot.rabbitmq.atm;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author liaonanzhou
 * @date 2020/9/21 14:56
 * @description
 **/
public class SettingHelper {


    public static ConnectionFactory init(ConnectionFactory connectionFactory) {
        // 配置连接信息
        connectionFactory.setHost("192.168.1.218");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        connectionFactory.setPort(5672);
        connectionFactory.setChannelRpcTimeout(3000);
        return connectionFactory;
    }

}
