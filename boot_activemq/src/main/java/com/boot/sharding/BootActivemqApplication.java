package com.boot.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1：队列模式：所有的消费者瓜分所有的任务
 * 2：订阅模式：所有的消费者都获取到这些任务
 * 3：管理界面地址：127.0.0.1:8161   Admin/Admin
 **/
@SpringBootApplication
public class BootActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootActivemqApplication.class, args);
    }

}
