/*
 * 文件名：CacheStudy.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： CacheStudy.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月12日
 * 修改内容：新增
 */
package guava.caching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

/**
 * 如果你不需要Cache中的特性，使用ConcurrentHashMap有更好的内存效率。
 * 
 * cache参数说明：
 * 
 * @author zxiaofan
 */
public class CacheStudy {
    /**
     * 缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]，其中包含移除原因[RemovalCause]、键和值.
     * 
     */
    RemovalListener<String, String> listener = new RemovalListener<String, String>() {

        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            System.out.println("RemovalListener:" + notification.getKey() + "," + notification.getValue() + "," + notification.getCause());
        }
    };

    /**
     * CacheBuilder生成器模式.
     */
    LoadingCache<String, String> cahceBuilder = CacheBuilder.newBuilder().removalListener(listener).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return "hello " + key;
        }

        /**
         * 自我实现批量加载模式.
         */
        public java.util.Map<String, String> loadAll(java.lang.Iterable<? extends String> keys) throws Exception {
            Map<String, String> map = new HashMap<>();
            for (String key : keys) {
                map.put(key, "hello" + key);
            }
            return map;
        };
    });

    /**
     * CacheBuilder生成器模式.
     */
    LoadingCache<String, String> cahceWeight = CacheBuilder.newBuilder().maximumWeight(20).weigher(new Weigher<String, String>() {

        @Override
        public int weigh(String key, String value) {
            return key.length();
        }
    }).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return "hello " + key;
        }
    });

    /**
     * Cache-定时回收.
     */
    LoadingCache<String, String> cahceTime = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return "hello " + key;
        }
    });

    /**
     * Cache-定时回收.
     */
    LoadingCache<String, String> cahceWriteTime = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return "hello " + key;
        }
    });

    /**
     * callable方式实现实例.
     */
    Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5).build();

    @Test
    public void cahceBuilderTest() {
        System.out.println(cahceBuilder.getIfPresent("pre")); // getIfPresent不存在则返回null
        System.out.println(cahceBuilder.getUnchecked("uncheck")); // getUnchecked不抛检查型异常（一旦CacheLoader声明了检查型异常，就不可以调用getUnchecked(K)）
        try {
            String value = cahceBuilder.get("csdn");
            System.out.println(value);
            cahceBuilder.put("hi", "zxiaofan");
            System.out.println(cahceBuilder.get("hi"));
            cahceBuilder.put("k1", "kk1");
            List<String> keys = Arrays.asList("k1", "k2");
            // getAll(Iterable<? extends K>)方法先从缓存取数据，没有对应value的key则会调用loadAll，筛选返回请求的键值对。
            Map<String, String> values = cahceBuilder.getAll(keys); // 批量查询,默认情况下，对每个不在缓存中的键，getAll方法会单独调用CacheLoader.load来加载缓存项
            // 如果批量加载比多个单独加载更高效，可以重载CacheLoader.loadAll。
            System.out.println(values.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // asMap:包含当前所有缓存项;asMap().get(key)实质上等同于cache.getIfPresent(key)，且不会引起缓存项的加载;所有读写操作都会重置相关缓存项的访问时间（get、put，不包括containsKey、entrySet）
        System.out.println(cahceBuilder.asMap().get("kk1"));
    }

    @Test
    public void callableTest() throws ExecutionException {
        String valcal = cache.get("zxiaofan", new Callable<String>() {
            public String call() {
                return "hello " + "zxiaofan";
            }
        });

        System.out.println(valcal);
        cache.put("cal", "github");
        System.out.println(cache.get("cal", new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "github_new";
            }
        }));

    }

    @Test
    public void evictionTest() {
        // 容量回收（size-based eviction）
        // maximumSize基于容量的回收,缓存项的数目逼近限定值时,回收最近没有使用或总体上很少使用的缓存项。
        for (int i = 0; i < 6; i++) {
            cache.put("k" + i, "v" + i);
        }
        System.out.println("maximumSize:" + cache.asMap().toString());
        // maximumWeight基于自定义权重的容量回收，回收是在weigh逼近限定值时进行，weigh是在缓存创建时计算，因此要考虑计算weigh的复杂度。
        for (int i = 0; i < 6; i++) {
            cahceWeight.put("kkkkk" + i, "v" + i); // maximumWeight(20)，weigh=key.length：只会存入2个元素
        }
        try {
            cahceWeight.get("123");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("cahceWeight:" + cahceWeight.asMap().toString());
        // 定时回收（Timed Eviction）
        // expireAfterAccess(long, TimeUnit)：缓存项在指定时间内没有被读/写访问，则回收，回收顺序同基于大小回收。
        // expireAfterWrite(long, TimeUnit)：缓存项在指定时间内没有被写访问（创建或覆盖），则回收。（如果认为缓存数据总是在固定时间后不可用，则这种回收方式可取）
        // CacheBuilder.ticker(Ticker)
        for (int i = 0; i < 6; i++) {
            cahceTime.put("k" + i, "v" + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("cahceTime:" + cahceTime.asMap().toString()); // expireAfterAccess(10, TimeUnit.SECONDS)10s过期

        // 引用回收（Reference-based Eviction）
        // 通过使用弱引用的键、或弱引用的值、或软引用的值，Guava Cache可以把缓存设置为允许垃圾回收：

        // CacheBuilder.weakKeys()：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键。
        // CacheBuilder.weakValues()：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是equals比较值。
        // CacheBuilder.softValues()：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（容量回收）。使用软引用值的缓存同样用==而不是equals比较值。
    }

    /**
     * 显示清除.
     * 
     */
    @Test
    public void removeTest() {
        for (int i = 0; i < 6; i++) {
            cahceBuilder.put("k" + i, "v" + i);
        }
        cahceBuilder.invalidate("k1"); // 单个清除
        System.out.println(cahceBuilder.asMap().toString());
        cahceBuilder.invalidateAll(Arrays.asList("k4", "k5")); // 批量清除
        System.out.println(cahceBuilder.asMap().toString());
        cahceBuilder.invalidateAll(); // 清除所有
        System.out.println(cahceBuilder.asMap().toString());
    }

    private Executor executor = Executors.newFixedThreadPool(3);

    /**
     * 异步 移除监听器.
     */
    LoadingCache<String, String> cahceAsyLis = CacheBuilder.newBuilder().removalListener(RemovalListeners.asynchronous(listener, executor)).build(new CacheLoader<String, String>() {

        @Override
        public String load(String key) throws Exception {
            return "hello " + key;
        }
    });

    /**
     * 移除监听器.
     * 
     * 监听器方法是在移除缓存时同步调用的。因为缓存的维护和请求响应通常是同时进行的，代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。
     * 
     * 可以使用RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作。
     * 
     * 
     */
    @Test
    public void removeListenerTest() {
        for (int i = 0; i < 6; i++) {
            cahceBuilder.put("k" + i, "v" + i);
        }
        cahceBuilder.invalidate("k1");
        System.out.println(cahceBuilder.asMap().toString());
    }

    /**
     * 何时会清理【主要在写操作、asMap时，偶尔读操作时】 & 实现定时清理.
     * 
     * CacheBuilder构建的缓存不会"自动"执行清理和回收工作，也不会在某个缓存项过期后马上清理，也没有诸如此类的清理机制。
     * 
     * 它会在【写操作、asMap】时顺带做少量的维护工作；如果写操作实在太少，会【偶尔在读操作】时做。
     * 
     * 如果要自动地持续清理缓存，就必须有一个线程，这个线程会和用户操作竞争共享锁。此外，某些环境下线程创建可能受限制，这样CacheBuilder就不可用了。
     * 
     * 如果缓存是高吞吐的，无需担心缓存的维护和清理。如果缓存只会偶尔有写操作，而你又不想清理工作阻碍了读操作，
     * 
     * 那么可以创建自己的维护线程，以固定的时间间隔调用Cache.cleanUp()。ScheduledExecutorService可以帮助你很好地实现这样的定时调度。
     * 
     * cleanUp() 并不会真正去remove键值对；可用invalidateAll()清除所有键值对。
     * 
     * @throws ExecutionException
     * 
     */
    @Test
    public void clearWhenTest() throws ExecutionException {
        for (int i = 0; i < 6; i++) {
            cahceTime.put("k" + i, "v" + i);
        }
        sleep(TimeUnit.SECONDS.toMillis(15)); // cahceTime:10s无读写则过期
        System.out.println(cahceTime.get("k1")); // 虽然k1过期依旧输出 k1
        System.out.println(cahceTime.get("k3")); // 虽然k2过期依旧输出 k3
        System.out.println(cahceTime.asMap().toString()); // 仅剩 k1、k3
        // cleanUp() 并不会真正去remove键值对；可用invalidateAll()清除所有键值对
        for (int i = 0; i < 6; i++) {
            cahceWriteTime.put("k" + i, "v" + i);
        }
        // 单独起线程 实现 定时回收
        ScheduledExecutorService schedu = Executors.newScheduledThreadPool(1);
        schedu.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                cahceWriteTime.cleanUp(); //
            }
        }, 0, 10, TimeUnit.SECONDS); // 初始化延迟0s，每隔10s执行一次
        // System.out.println(cahceWriteTime.asMap().toString());
        cahceWriteTime.cleanUp();
        sleep(TimeUnit.SECONDS.toMillis(15)); // cahceTime:10s无读写则过期
        cahceWriteTime.cleanUp();
        System.out.println(cahceWriteTime.get("k1")); // 虽然k1过期依旧输出 k1 ?????
        System.out.println(cahceWriteTime.get("k3")); // 虽然k2过期依旧输出 k3 ?????
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
