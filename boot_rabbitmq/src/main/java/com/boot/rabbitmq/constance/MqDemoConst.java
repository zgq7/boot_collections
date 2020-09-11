package com.boot.rabbitmq.constance;

import java.time.LocalTime;
import java.util.concurrent.*;

/**
 * @author liaonanzhou
 * @date 2020/8/25 15:21
 * @description
 **/
public class MqDemoConst {

    public static final String MQ_DEMO_INPUT = "mq-demo-input";

    public static final String MQ_DEMO_OUTPUT = "mq-demo-output";

    public static final String MQ_DEMO_REDIS_KEY = "mq:demo:redis:";

    public static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(5,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10)
            );


}
