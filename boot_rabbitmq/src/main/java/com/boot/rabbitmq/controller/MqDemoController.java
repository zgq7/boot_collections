package com.boot.rabbitmq.controller;

import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.producer.CommitProducer;
import com.boot.rabbitmq.producer.DevProducer;
import com.boot.rabbitmq.producer.PartitionProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private DevProducer devProducer;
    @Resource
    private PartitionProducer mqPartitionProducer;
    @Resource
    private CommitProducer commitProducer;

    @PostMapping(value = "/dev")
    public void dev(@RequestBody MqModel model) {
        devProducer.sendMsg(model);
    }

    @PostMapping(value = "/partition")
    public void partition(@RequestBody MqModel model) {
        mqPartitionProducer.sendMsg(model);
    }

    @PostMapping(value = "/condition")
    public void condition(@RequestBody MqModel model) {
        commitProducer.sendMsg(model);
    }

}
