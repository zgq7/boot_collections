package com.boot.rabbitmq.controller;

import com.boot.rabbitmq.producer.MqDemoProducer;
import com.boot.rabbitmq.producer.MqPartitionProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liaonanzhou
 * @date 2020/8/25 15:09
 * @description
 **/
@RestController
public class MqDemoController {

    @Resource
    private MqDemoProducer mqDemoProducer;
    @Resource
    private MqPartitionProducer mqPartitionProducer;

    @PostMapping(value = "/test")
    public void test() {
        mqDemoProducer.sendMsg();
    }

    @PostMapping(value = "/partition")
    public void partition() {
        mqPartitionProducer.sendPartitionMsg();
    }

}
