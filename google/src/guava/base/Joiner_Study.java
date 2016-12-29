/*
 * 文件名：Joiner_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Joiner_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月29日
 * 修改内容：新增
 */
package guava.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;

/**
 * 连接器Joiner.join(Iterable<?> parts)
 * 
 * joiner实例总是不可变的，线程安全，建议定义为static final常量。
 * 
 * @author zxiaofan
 */
public class Joiner_Study {
    String str1 = "a ";

    String str2 = "";

    String str_null = null;

    String str4 = "d";

    String[] arr = new String[]{"a", null, "c"};

    static final Joiner joiner = Joiner.on(";").skipNulls(); // 以;分隔，忽略null

    static final MapJoiner mapJoin = Joiner.on(";").withKeyValueSeparator("-->"); // key-value分隔符

    @Test
    public void basicTest() {
        String result = joiner.join(str1, str2, str_null, str4);
        System.out.println(result); // a ;;d
        Joiner joiner2 = Joiner.on("&&").useForNull("default"); // 以&&分隔,使用default替换null
        result = joiner2.join(str1, str2, str_null, str4);
        System.out.println(result); // a &&&&default&&d
        // 错误用法
        Joiner err = Joiner.on(";");
        err.skipNulls(); // does nothing!
        System.out.println(err.join(str2, str_null, str4)); // NullPointerException
    }

    /**
     * appendTo拼接StringBuffer需捕获IOException，拼接StringBuilder则不需要.
     * 
     */
    @Test
    public void otherTest() {
        String result = joiner.join(arr);
        System.out.println(result); // a;c
        StringBuffer buf = new StringBuffer();
        buf.append("buf");
        StringBuffer res = new StringBuffer();
        try {
            res = joiner.appendTo(buf, arr); // appendTo直接拼接
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res.toString()); // bufa;c
        StringBuilder build = new StringBuilder("build");
        System.out.println(joiner.appendTo(build, arr)); // builda;c
    }

    @Test
    public void mapJoinerTest() {
        Map<String, String> map = new HashMap();
        map.put("name", "zxiaofan.com");
        map.put("age", "20");
        String result = mapJoin.join(map);
        System.out.println(result);
    }

    /**
     * MapJoiner遍历map比传统遍历map.entrySet()慢很多（but百万级别数据拼接在1s以内，so常规程序可以忽略这点性能差距）。
     * 
     * Joiner遍历List在百万级别以上时比传统方式更高效。
     * 
     * Joiner优势：代码更优雅（null；末尾的分隔符）。
     * 
     */
    @Test
    public void performanceCompare() {
        System.out.println("----拼接Map---");
        joinCompareMap(5000);
        joinCompareMap(10000);
        joinCompareMap(100000);
        joinCompareMap(1000000);
        // ---key_value数量:5000---
        // 传统遍历拼接方式：2ms
        // MapJoin拼接方式：4ms
        // ---key_value数量:10000---
        // 传统遍历拼接方式：4ms
        // MapJoin拼接方式：5ms
        // ---key_value数量:100000---
        // 传统遍历拼接方式：27ms
        // MapJoin拼接方式：40ms
        // ---key_value数量:1000000---
        // 传统遍历拼接方式：147ms
        // MapJoin拼接方式：622ms
        System.out.println();
        System.out.println("----拼接List---");
        joinCompareList(5000);
        joinCompareList(10000);
        joinCompareList(100000);
        joinCompareList(500000);
        joinCompareList(1000000); // 百万
        // ---key_value数量:5000---
        // 传统遍历拼接方式：1ms
        // Joiner拼接方式：1ms
        // ---key_value数量:10000---
        // 传统遍历拼接方式：2ms
        // Joiner拼接方式：2ms
        // ---key_value数量:100000---
        // 传统遍历拼接方式：6ms
        // Joiner拼接方式：21ms
        // ---key_value数量:500000---
        // 传统遍历拼接方式：21ms
        // Joiner拼接方式：43ms
        // ---key_value数量:1000000---
        // 传统遍历拼接方式：66ms
        // Joiner拼接方式：33ms
    }

    private void joinCompareMap(int num) {
        System.out.println("// ---key_value数量:" + num + "---");
        Map<String, String> map = new HashMap();
        for (int i = 0; i < num; i++) {
            map.put("k" + i, "val" + i);
        }
        long time1 = System.currentTimeMillis();
        StringBuilder buf = new StringBuilder(); // 单线程StringBuilder，多线程StringBuffer
        for (Entry<String, String> entry : map.entrySet()) {
            buf.append(entry.getKey()).append("-->").append(entry.getValue()).append(";");
        }
        long time2 = System.currentTimeMillis();
        System.out.println("// 传统遍历拼接方式：" + (time2 - time1) + "ms");
        time1 = System.currentTimeMillis();
        String result = mapJoin.join(map);
        time2 = System.currentTimeMillis();
        System.out.println("// MapJoin拼接方式：" + (time2 - time1) + "ms");
    }

    private void joinCompareList(int num) {
        System.out.println("// ---key_value数量:" + num + "---");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("val" + i);
        }
        long time1 = System.currentTimeMillis();
        StringBuilder buf = new StringBuilder();
        for (String entry : list) {
            buf.append(entry).append(";");
        }
        long time2 = System.currentTimeMillis();
        System.out.println("// 传统遍历拼接方式：" + (time2 - time1) + "ms");
        time1 = System.currentTimeMillis();
        String result = joiner.join(list);
        time2 = System.currentTimeMillis();
        System.out.println("// Joiner拼接方式：" + (time2 - time1) + "ms");
    }
}
