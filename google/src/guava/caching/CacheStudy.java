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
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * 如果你不需要Cache中的特性，使用ConcurrentHashMap有更好的内存效率。
 * get(key) ：入参key不允许为null，否则抛异常com.google.common.cache.CacheLoader$InvalidCacheLoadException: CacheLoader returned null for key "key"；
 * get(key) ：返回值不允许为null，否则抛异常java.lang.NullPointerException at com.google.common.base.Preconditions.checkNotNull(Preconditions.java:770)。
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
    LoadingCache<String, String> cacheBuilder = CacheBuilder.newBuilder().removalListener(listener).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return initValue(key);
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
    LoadingCache<String, String> cacheWeight = CacheBuilder.newBuilder().maximumWeight(20).weigher(new Weigher<String, String>() {

        @Override
        public int weigh(String key, String value) {
            return key.length();
        }
    }).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return initValue(key);
        }
    });

    /**
     * Cache-定时回收.
     */
    LoadingCache<String, String> cacheTime = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return initValue(key);
        }
    });

    /**
     * Cache-定时回收.
     */
    LoadingCache<String, String> cacheWriteTime = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
            return initValue(key);
        }
    });

    /**
     * callable方式实现实例.
     */
    Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5).build();

    @Test
    public void cahceBuilderTest() {
        System.out.println(cacheBuilder.getIfPresent("pre")); // getIfPresent不存在则返回null
        System.out.println(cacheBuilder.getUnchecked("uncheck")); // getUnchecked不抛检查型异常（一旦CacheLoader声明了检查型异常，就不可以调用getUnchecked(K)）
        try {
            String value = cacheBuilder.get("csdn");
            System.out.println(value);
            cacheBuilder.put("hi", "zxiaofan");
            System.out.println(cacheBuilder.get("hi"));
            cacheBuilder.put("k1", "kk1");
            List<String> keys = Arrays.asList("k1", "k2");
            // getAll(Iterable<? extends K>)方法先从缓存取数据，没有对应value的key则会调用loadAll，筛选返回请求的键值对。
            Map<String, String> values = cacheBuilder.getAll(keys); // 批量查询,默认情况下，对每个不在缓存中的键，getAll方法会单独调用CacheLoader.load来加载缓存项
            // 如果批量加载比多个单独加载更高效，可以重载CacheLoader.loadAll。
            System.out.println(values.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // asMap:包含当前所有缓存项;asMap().get(key)实质上等同于cache.getIfPresent(key)，且不会引起缓存项的加载;
        // 所有读写操作都会重置相关缓存项的访问时间（get、put，不包括containsKey、entrySet）
        System.out.println(cacheBuilder.asMap().get("kk1"));
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
            cacheWeight.put("kkkkk" + i, "v" + i); // maximumWeight(20)，weigh=key.length：只会存入2个元素
        }
        try {
            cacheWeight.get("123");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("cahceWeight:" + cacheWeight.asMap().toString());
        // 定时回收（Timed Eviction）
        // expireAfterAccess(long, TimeUnit)：缓存项在指定时间内没有被读/写访问，则回收，回收顺序同基于大小回收。
        // expireAfterWrite(long, TimeUnit)：缓存项在指定时间内没有被写访问（创建或覆盖），则回收。（如果认为缓存数据总是在固定时间后不可用，则这种回收方式可取）
        // CacheBuilder.ticker(Ticker)
        for (int i = 0; i < 6; i++) {
            cacheTime.put("k" + i, "v" + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("cahceTime:" + cacheTime.asMap().toString()); // expireAfterAccess(10, TimeUnit.SECONDS)10s过期

        // 引用回收（Reference-based Eviction）
        // 通过使用弱引用的键、或弱引用的值、或软引用的值，Guava Cache可以把缓存设置为允许垃圾回收：

        // CacheBuilder.weakKeys()：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键。
        // CacheBuilder.weakValues()：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是equals比较值。
        // CacheBuilder.softValues()：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。
        // 考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（容量回收）。使用软引用值的缓存同样用==而不是equals比较值。
    }

    /**
     * 显示清除.
     * 
     */
    @Test
    public void removeTest() {
        initMap(cacheBuilder);
        cacheBuilder.invalidate("k1"); // 单个清除
        System.out.println(cacheBuilder.asMap().toString());
        cacheBuilder.invalidateAll(Arrays.asList("k4", "k5")); // 批量清除
        System.out.println(cacheBuilder.asMap().toString());
        cacheBuilder.invalidateAll(); // 清除所有
        System.out.println(cacheBuilder.asMap().toString());
    }

    private Executor executor = Executors.newFixedThreadPool(3);

    /**
     * 异步 移除监听器.
     */
    LoadingCache<String, String> cahceAsyLis = CacheBuilder.newBuilder().removalListener(RemovalListeners.asynchronous(listener, executor)).build(new CacheLoader<String, String>() {

        @Override
        public String load(String key) throws Exception {
            return initValue(key);
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
        initMap(cacheBuilder);
        cacheBuilder.invalidate("k1");
        System.out.println(cacheBuilder.asMap().toString());
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
     * cleanUp针对所有过期数据，refresh针对指定key。
     * 
     * @throws ExecutionException
     * 
     */
    @Test
    public void clearWhenTest() throws ExecutionException {
        initMap(cacheTime);
        sleep(TimeUnit.SECONDS.toMillis(15)); // cahceTime:10s无读写则过期
        // Note:不能用get，因为无数据将load
        System.out.println(cacheTime.getIfPresent("k1")); // null
        System.out.println(cacheTime.getIfPresent("k3")); // null
        System.out.println(cacheTime.asMap().toString()); // 无数据
        //
        initMap(cacheWriteTime);
        // 单独起线程 实现 定时回收
        ScheduledExecutorService schedu = Executors.newScheduledThreadPool(1);
        schedu.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                cacheWriteTime.cleanUp(); //
            }
        }, 0, 10, TimeUnit.SECONDS); // 初始化延迟0s，每隔10s执行一次
        // System.out.println(cahceWriteTime.asMap().toString());
        sleep(TimeUnit.SECONDS.toMillis(15)); // cacheWriteTime:10s无写则过期
        System.out.println(cacheWriteTime.getIfPresent("k1")); // null
        System.out.println(cacheWriteTime.getIfPresent("k3")); // null
    }

    /**
     * 缓存刷新-异步
     * 
     * （重写reload自定义刷新方法,允许开发者在计算新值时使用旧的值）.
     * 
     * refreshAfterWrite自动定时刷新。缓存项只有在被检索时才会真正刷新（如果CacheLoader.refresh实现为异步，那么检索不会被刷新拖慢）。
     * 
     * 如果你在缓存上同时声明expireAfterWrite和refreshAfterWrite，缓存并不会因为刷新盲目地定时重置，如果缓存项没有被检索，那刷新就不会真的发生，缓存项在过期时间后也变得可以回收。
     */
    LoadingCache<String, String> cacheRefresh = CacheBuilder.newBuilder().refreshAfterWrite(5, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {

        @Override
        public String load(String key) throws Exception {
            return "hello " + key + (int) (Math.random() * 10);
        }

        public ListenableFuture<String> reload(final String key, String oldValue) throws Exception {
            if (neverNeedRefresh(key)) { // 该key【不需要】刷新
                return Futures.immediateFuture(oldValue); // oldValue为null则返回new ImmediateSuccessfulFuture<Object>(null)，否则返回原值
            } else { // asynchronous
                ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "hello new " + key;
                    }
                });
                executor.execute(task); // 推荐 异步刷新
                return task;
            }
        }

        /**
         * 该key是否需要刷新.
         */
        private boolean neverNeedRefresh(String key) {
            return false;
        };
    });

    /**
     * 缓存刷新-同步.
     */
    LoadingCache<String, String> cacheSyncRefresh = CacheBuilder.newBuilder().refreshAfterWrite(5, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {

        @Override
        public String load(String key) throws Exception {
            return "hello " + key + (int) (Math.random() * 10);
        }
    });

    /**
     * 刷新：加载新值，可异步，刷新时get仍可返回旧值（和回收操作缓存不同，回收操作必须等待新值加载完成）.
     * 
     * 缓存项只有在【被检索（get、getIfPresent）时才会真正刷新,asMap不会触发刷新操作】（如果CacheLoader.refresh实现为【异步】，那么检索不会被刷新拖慢，但【返回值可能是旧数据】）.
     * 
     * ###【refresh针对单个key】同步refresh：过期后get立即返回新值（耗时）；异步refresh：过期后get触发refresh，再次get【可能】返回新值。###
     * 
     * 当key n秒后没有被写操作时，不会自动清理，需要再次调用get(key)方法时才会触发刷新操.
     * 
     * 刷新时抛出异常，缓存将保留旧值，异常记录到日志后将被丢弃。
     * 
     * 重载CacheLoader.reload(K, V)可以扩展刷新时的行为，此方法允许开发者在计算新值时使用旧的值。
     * 
     * @throws ExecutionException
     */
    @Test
    public void refreshTest() throws ExecutionException {
        System.out.println("异步刷新...");
        initMap(cacheRefresh);
        System.out.println(cacheRefresh.asMap().toString());
        cacheRefresh.put("k6", "refresh start");
        System.out.println(cacheRefresh.getIfPresent("k2"));
        sleep(TimeUnit.SECONDS.toMillis(10));
        System.out.println("data timeOut...");
        System.out.println(cacheRefresh.getIfPresent("k2"));
        System.out.println(cacheRefresh.asMap().toString());
        for (int i = 0; i < 3; i++) {
            System.out.println("如果timeOut，则触发refresh：" + i + ":" + cacheRefresh.get("k" + i)); // 调用get操作，对指定key触发refresh
        }
        System.out.println(cacheRefresh.asMap().toString());
        for (int i = 0; i < 7; i++) {
            System.out.println(i + ":" + cacheRefresh.getIfPresent("k" + i)); // 已get过的key将返回新value
        }
        // 同步刷新
        System.out.println("同步刷新...");
        initMap(cacheSyncRefresh);
        sleep(TimeUnit.SECONDS.toMillis(10));
        System.out.println(cacheSyncRefresh.asMap().toString());
        System.out.println("data timeOut...");
        for (int i = 0; i < 7; i++) {
            System.out.println("只要timeOut，get即refresh：" + i + ":" + cacheSyncRefresh.getIfPresent("k" + i)); // 已get过的key将返回新value
        }
    }

    /**
     * 缓存统计.
     */
    LoadingCache<String, String> cacheStats = CacheBuilder.newBuilder().recordStats().build(new CacheLoader<String, String>() {

        @Override
        public String load(String key) throws Exception {
            return initValue(key);
        }
    });

    /**
     * 统计。Cache.stats()方法会返回CacheStats对象以提供统计信息。
     * 
     * hitRate()缓存命中率;averageLoadPenalty()加载新值的平均时间，单位为纳秒；evictionCount()缓存项被回收的总数，不包括显式清除；
     * 
     * loadSuccessCount缓存加载新值的成功次数。
     * 
     * @throws ExecutionException
     * 
     */
    @Test
    public void statsTest() throws ExecutionException {
        initMap(cacheStats);
        for (int i = 0; i < 9; i++) {
            System.out.println(i + ":" + cacheStats.getIfPresent("k" + i));
        }
        System.out.println(cacheStats.stats().hitRate() + "," + cacheStats.stats());
        for (int i = 4; i < 9; i++) {
            System.out.println(i + ":" + cacheStats.get("k" + i));
        }
        System.out.println(cacheStats.stats().hitRate() + "," + cacheStats.stats());
    }

    /**
     * asMap.
     * 
     * cache.asMap()包含当前所有加载到缓存的项，就相当于一个Map。
     * 
     * 对cache.asMap的操作相当于对cache的操作。
     * 
     * asMap().get(key)实质上等同于cache.getIfPresent(key)，而且不会引起缓存项的加载，这和Map的语义约定一致。
     * 
     * 所有读写操作都会重置相关缓存项的访问时间，包括Cache.asMap().get(Object)方法和Cache.asMap().put(K, V)方法。
     * 
     * 但不包含：asMap().containsKey()、asMap()的集合视图上的操作（比如遍历asMap().entrySet()）。
     * 
     * Guava Cache 无containsKey()
     * 
     */
    @Test
    public void asMapTest() {
        initMap(cacheBuilder);
        Map<String, String> map = cacheBuilder.asMap();
        System.out.println(map);
        map.put("k1", "asMap");
        System.out.println(cacheBuilder.asMap().toString());
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存无数据时，生成value.
     * 
     * @param key
     * @return
     */
    private String initValue(String key) {
        return "hello " + key;
    }

    /**
     * 初始化map.
     * 
     * @param key
     * @return
     */
    private void initMap(LoadingCache<String, String> cach) {
        System.out.println("初始化map...");
        for (int i = 0; i < 6; i++) {
            cach.put("k" + i, "v" + i);
        }
    }
}
