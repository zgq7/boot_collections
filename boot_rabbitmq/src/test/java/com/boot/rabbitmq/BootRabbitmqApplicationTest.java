package com.boot.rabbitmq;

import com.boot.rabbitmq.constance.MQModel;
import com.boot.rabbitmq.producer.CommitProducer;
import com.boot.rabbitmq.producer.DevProducer;
import com.boot.rabbitmq.producer.PartitionProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zgq7
 * @date 2020/10/30 11:46
 * @description
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootRabbitmqApplicationTest {

    @Resource
    private DevProducer devProducer;
    @Resource
    private PartitionProducer mqPartitionProducer;
    @Resource
    private CommitProducer commitProducer;

}