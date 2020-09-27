package com.boot.rabbitmq.constance;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2020/8/25 15:21
 * @description
 **/
public class MqConstants {

    public static final String MQ_DEMO_INPUT = "mq-demo-input";

    public static final String MQ_DEMO_OUTPUT = "mq-demo-output";

    public static final String MQ_PARTITION_INPUT = "mq-partition-input";

    public static final String MQ_PARTITION_OUTPUT = "mq-partition-output";

    public static final String MQ_DEMO_REDIS_KEY = "mq:demo:redis:";

    public static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(5,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10)
            );


}
