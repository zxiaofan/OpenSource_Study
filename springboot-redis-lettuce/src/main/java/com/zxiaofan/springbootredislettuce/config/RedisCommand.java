package com.zxiaofan.springbootredislettuce.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;

/**
 * Project: OpenSource_Study
 * Author: zxiaofan
 * Date: 2019/4/22
 * Time: 19:09
 * Desc: RedisCommand，可自定义连接。此处为示例，线上项目建议从配置文件获取相关配置.
 */
//@Component
public class RedisCommand {
    private static RedisClient redisClient = null;
    private static RedisCommands<String, String> redisCommandsSync = null;

    /*服务启动时连接校验Redis配置是否正确*/
    static {
        if (null == redisClient) {
            // RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
            RedisURI redisURI = RedisURI.Builder.redis("localhost", 6379).withPassword("").withDatabase(1).withTimeout(Duration.ofSeconds(10)).withClientName("redisclient_xiaofan").build();
            redisClient = RedisClient.create(redisURI);
        }
    }

    /**
     * Description:获取同步的线程安全的RedisAPI.
     *
     * @return syncRedisCommands
     */
    public static RedisCommands<String, String> getRedisCommandsSync() {
        if (null != redisCommandsSync) {
            return redisCommandsSync;
        }
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        redisCommandsSync = connection.sync();
        return redisCommandsSync;
    }
}
