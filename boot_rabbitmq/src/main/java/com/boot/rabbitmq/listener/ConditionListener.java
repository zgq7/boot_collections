package com.boot.rabbitmq.listener;

import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.stream.RabbitStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author zgq7
 * @date 2020/9/1 10:59
 * @description
 **/
@Component
@EnableBinding(RabbitStream.class)
public class ConditionListener {

    private static final Logger logger = LoggerFactory.getLogger(ConditionListener.class);

    @StreamListener(target = MqConstants.DEV_EXCHANGE, condition = "headers.get('mid') < 100")
    public void xiaoyuMsg(@Payload String payload,
                          @Headers MessageHeaders headers) {
        logger.info("xiaoyu {},{}", payload, headers.get("mid"));
    }

    @StreamListener(target = MqConstants.DEV_EXCHANGE, condition = "headers.get('mid') >= 100")
    public void dayuMsg(@Payload String payload,
                        @Headers MessageHeaders headers) {
        logger.info("dayu {},{}", payload, headers.get("mid"));
    }

}
