/*
 * 文件名：RateLimiter_Study.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： RateLimiter_Study.java
 * 修改人：xiaofan
 * 修改时间：2017年1月15日
 * 修改内容：新增
 */
package guava.util.concurrent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * RateLimiter,令牌桶限流算法。
 * 
 * RateLimiter会按照一定的频率往桶里扔令牌，线程拿到令牌才能执行。RateLimiter并不提供公平性的保证。
 * 
 * 创建RateLimiter：static RateLimiter create(double permitsPerSecond)创建指定QPS的限流器；static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit
 * unit)创建指定吞吐量和预热期的限流器（warmupPeriod、unit在此时间内RateLimiter会增加它的速率直至最大）；
 * 
 * 获取许可：double acquire()获取1个许可；double acquire(int permits)或许制定个数许可；
 * 
 * 稳定速率：double getRate()，初始值为RateLimiter的构造函数值，只有在调用setRate(double)后才会被更新；void setRate(double permitsPerSecond)紧跟着的下一个请求不会被最新的速率影响。
 * 
 * 是否能获取许可： boolean tryAcquire()是否可立即获取一个许可；tryAcquire(int permits)是否可立即获取制定个数许可；tryAcquire(long timeout, TimeUnit unit)是否可在指定时间内获取一个许可；tryAcquire(int permits, long timeout, TimeUnit
 * unit)是否可在指定时间内获取指定数量许可。
 * 
 * @author xiaofan
 */
public class RateLimiter_Study {
    final RateLimiter rateLimiter = RateLimiter.create(2.0); // 每秒2个许可

    final RateLimiter rateStream = RateLimiter.create(100.0); //

    final RateLimiter rateAcquire = RateLimiter.create(20.0); // 令牌桶数量为20，即每50ms产生一个令牌

    String randomStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    AtomicInteger atomic = new AtomicInteger(0);

    @Test
    public void basicTest() {
        // 每秒两个许可
        for (int i = 0; i < 100; i++) {
            rateLimiter.acquire(); // return acquire(1); 请求一块令牌
            executeTask(); // 每秒执行两次
        }
    }

    /**
     * 以每秒0.1kb的速率处理数据流（假设每个字节代表一个许可）.
     * 
     */
    @Test
    public void StreamTest() {
        List<String> listStr = createRandomStr(100);
        System.out.println(listStr.toString());
        // 每秒100个许可
        for (String str : listStr) {
            rateStream.acquire(str.getBytes().length); // 请求str.getBytes().length块令牌
            executeTask(); // 每秒执行两次
        }
    }

    /**
     * 
     */
    @Test
    public void AcquireTest() {
        sleep(2000); // 确保RateLimiter已初始化完毕（1s才能产生getRate个令牌）
        System.out.println("Rate:" + rateAcquire.getRate() + ",每" + 1000 / rateAcquire.getRate() + "ms产生一个令牌");
        for (int i = 0; i < 10000000; i++) {
            if (rateAcquire.tryAcquire()) { // MICROSECONDS微秒
                executeTask();
            } else { // 若每个任务结束后不休眠，令牌使用完毕后，每隔1000/rateAcquire.getRate()毫秒后才能为true
                // System.err.println(atomic.addAndGet(1) + "_no acquire");
            }
            // sleep(10);
        }
    }

    /**
     * 休眠.
     * 
     * @param i
     *            毫秒
     * 
     * @throws InterruptedException
     */
    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成num个指定长度区间的字符串.
     * 
     * @return
     */
    private List<String> createRandomStr(int num) {
        List<String> list = new ArrayList<>(100);
        for (int i = 0; i < num; i++) {
            StringBuffer sb = new StringBuffer();
            Random random = new Random();
            int length = (int) (Math.random() * 20) + 10; // 字符串长度（10,30位）
            for (int j = 0; j < length; j++) {
                int number = random.nextInt(62);
                sb.append(randomStr.charAt(number));
            }
            list.add(sb.toString());
        }
        return list;
    }

    /**
     * 执行任务.
     * 
     */
    private void executeTask() {
        DateFormat format = new SimpleDateFormat("HH:mm:ss.S");
        System.err.println(atomic.addAndGet(1) + "_" + format.format(new Date()));
    }
}
