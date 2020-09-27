package com.boot.rabbitmq.listener;

import com.boot.rabbitmq.constance.MqConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author liaonanzhou
 * @date 2020/9/1 10:59
 * @description
 **/
//@Component
//@EnableBinding(MqDemoConditionListener.class)
public class MqDemoConditionListener {

    private static final Logger logger = LoggerFactory.getLogger(MqDemoConditionListener.class);

    /**
     * 可按条件进行消息处理
     * condition 中的 payload 代表参数，可调用参数的方法
     **/
    @StreamListener(target = MqConstants.MQ_DEMO_INPUT, condition = "payload.length < 100")
    public void xiaoyuMsg(@Payload String payload) {
        logger.info("xiaoyu {}", payload);
    }

    /**
     * 可按条件进行消息处理
     **/
    @StreamListener(target = MqConstants.MQ_DEMO_INPUT, condition = "payload.length >= 100")
    public void dayuMsg(@Payload String payload) {
        logger.info("dayu {}", payload);
    }

}
