package com.boot.rabbitmq.producer;

import com.boot.rabbitmq.constance.MqModel;
import com.boot.rabbitmq.stream.MqDemoStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author liaonanzhou
 * @date 2020/9/17 18:29
 * @description
 **/
@Component
@EnableBinding(MqDemoStream.class)
public class MqPartitionProducer {

    public MqPartitionProducer(MqDemoStream mqDemoStream) {
        this.mqDemoStream = mqDemoStream;
    }

    private final MqDemoStream mqDemoStream;

    public void sendPartitionMsg() {
        MqModel model = new MqModel(999, 1L, 1);
        mqDemoStream.mqPartitionOutput()
                .send(MessageBuilder.withPayload(model)
                        .setHeader("mid", model.getMid())
                        .build()
                );
    }

}
