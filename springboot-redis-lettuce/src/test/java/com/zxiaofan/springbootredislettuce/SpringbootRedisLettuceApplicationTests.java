package com.zxiaofan.springbootredislettuce;

import com.zxiaofan.springbootredislettuce.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisLettuceApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void getset() {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        String intinc = "intinc";
        IntStream.range(0, 1000).forEach(i ->
                executorService.execute(() -> stringRedisTemplate.opsForValue().increment(intinc, 1))
        );
        // 休眠几秒，保证异步线程执行完毕
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("intinc:" + stringRedisTemplate.opsForValue().get("intinc"));
        String key = "model:books";
        redisTemplate.opsForValue().set(key, new Book("Java进阶", "AAA"));
        Book user = (Book) redisTemplate.opsForValue().get(key);
        System.out.println(null == user ? "null" : user.toString());
    }

}
