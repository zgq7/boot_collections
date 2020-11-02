package com.boot.rabbitmq.constance;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2020/8/25 15:21
 * @description
 **/
public class MQConstants {

    public static final String DEV_EXCHANGE = "dev-exchange";

    public static final String PARTITION_EXCHANGE = "partition-exchange";

    public static final String COMMIT_EXCHANGE = "commit-exchange";

    public static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(5,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(10)
            );


}
