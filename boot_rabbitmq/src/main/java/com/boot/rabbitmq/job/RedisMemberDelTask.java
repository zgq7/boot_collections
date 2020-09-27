package com.boot.rabbitmq.job;

import com.boot.rabbitmq.constance.MqConstants;
import com.boot.rabbitmq.constance.MqModel;
import com.boot.redis.RedisComUtil;
import com.boot.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author liaonanzhou
 * @date 2020/9/1 15:20
 * @description
 **/
@Component
public class RedisMemberDelTask {

    private static final Logger logger = LoggerFactory.getLogger(RedisMemberDelTask.class);

    private final RedisUtil redisUtil;
    private final RedisComUtil redisComUtil;

    public RedisMemberDelTask(RedisUtil redisUtil, RedisComUtil redisComUtil) {
        this.redisUtil = redisUtil;
        this.redisComUtil = redisComUtil;
    }

    /**
     * @apiNote 1:每个 member 的生存时间为 10s
     * @apiNote 2:目的是防止redis保存的消息序号队列中不存在了，导致消息一致重入队列
     * @apiNote 3:集群环境采用 xxl-job
     **/
    @Scheduled(cron = "0/10 * * * * ?")
    public void redisMemberDelTask() {
        ThreadPoolExecutor executor = MqConstants.EXECUTOR;
        redisUtil.getKeys(MqConstants.MQ_DEMO_REDIS_KEY + "*")
                .forEach(key -> executor.execute(() -> {
                    List<Object> memberList = new ArrayList<>(redisUtil.zRange(key, 0, 0));
                    if (memberList.size() > 0) {
                        final long maxScore = System.currentTimeMillis();
                        final long minScore = Optional.ofNullable(redisUtil.zScore(key, memberList.get(0)))
                                .orElse(Double.MAX_VALUE).longValue();
                        if (minScore <= maxScore) {
                            // 删除过期的member
                            Set<Object> redisObj = redisUtil.zRangeByScore(key, minScore, maxScore, 0, 10);
                            List<String> rms = new ArrayList<>(redisObj).stream().map(String::valueOf).collect(Collectors.toList());
                            long count = redisUtil.zRemRangeByScore(key, MqModel.parseVersion(rms.get(0)).doubleValue(),
                                    MqModel.parseVersion(rms.get(rms.size() - 1)).doubleValue());
                            logger.info("count:{},rms->{}", count, rms);
                        }
                    }
                }));
    }

}
