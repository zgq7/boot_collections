package com.boot.rabbitmq.controller;

import com.boot.rabbitmq.listener.MqDemoListener;
import com.boot.rabbitmq.producer.MqDemoProducer;
import com.google.common.collect.Comparators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;

/**
 * @author liaonanzhou
 * @date 2020/8/25 15:09
 * @description
 **/
@RestController
public class MqDemoController {

    @Resource
    private MqDemoProducer mqDemoProducer;

    @PostMapping(value = "/test")
    public void test() {
        mqDemoProducer.sendMsg();
    }

}
