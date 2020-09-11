package com.boot.redis;

import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author liaonanzhou
 * @date 2020/9/3 15:37
 * @description redisTemplate 无法支持的语法放这里
 **/
public class RedisComUtil {

    private final GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool;

    public RedisComUtil(GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool) {
        this.redisConnectionPool = redisConnectionPool;
    }

    /**
     * @param key redis key
     * @return 弹出并删除score最大的member
     **/
    public ScoredValue<String> zPopMax(String key) {
        try (StatefulRedisConnection<String, String> connection = redisConnectionPool.borrowObject()) {
            RedisCommands<String, String> commands = connection.sync();
            return commands.zpopmax(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long zRem(String key, String... members) {
        try (StatefulRedisConnection<String, String> connection = redisConnectionPool.borrowObject()) {
            RedisCommands<String, String> commands = connection.sync();
            return commands.zrem(key, members);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
