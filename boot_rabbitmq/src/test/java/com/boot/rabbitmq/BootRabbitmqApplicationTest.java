package com.boot.rabbitmq;

import com.boot.rabbitmq.constance.MqModel;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BootRabbitmqApplication.class)
public class BootRabbitmqApplicationTest {

    @Resource
    private CommitProducer commitProducer;

    @Test
    public void conditionTest() {
        MqModel model = new MqModel();
        model.setMid(1);
        model.setSno(1);
        for (int i = 0; i < 100; i++) {
            model.setVersion(System.currentTimeMillis());
            commitProducer.sendMsg(model);
        }
    }

}