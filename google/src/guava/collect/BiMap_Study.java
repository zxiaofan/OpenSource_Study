/*
 * 文件名：BiMap_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BiMap_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月26日
 * 修改内容：新增
 */
package guava.collect;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

/**
 * inverse()反转BiMap<K, V>的键值映射.inverse的map是一个视图，在这个反转后的map中的任何增删改操作都会影响原来的map。
 * 
 * key/value均唯一，因此 values()返回的是Set而不是普通的Collection.
 * 
 * 把键映射到已经存在的值，会抛出IllegalArgumentException异常。想要强制替换特定值的键，使用 BiMap.inverse().forcePut(key, value)。
 * 
 * （key-value随意put，value-key使用inverse().forcePut强制替换）
 * 
 * @author zxiaofan
 */
// Key-Value Map Impl--Value-Key Map Impl--Corresponding BiMap
// HashMap--HashMap--HashBiMap
// ImmutableMap--ImmutableMap--ImmutableBiMap
// EnumMap--EnumMap--EnumBiMap
// EnumMap--HashMap--EnumHashBiMap.
public class BiMap_Study {
    /**
     * BiMap基础方法，其他实现类亦有如下方法.
     * 
     */
    @Test
    public void basicTest() {
        BiMap<String, Integer> nameId = HashBiMap.create();
        initMap(nameId);
        System.err.println(nameId.get("a")); // 1
        System.err.println(nameId.inverse().get(2)); // b, value-->key
        nameId.putIfAbsent("c", 33); // 不存在则插入
        System.err.println(nameId.inverse().get(3)); // c
        nameId.put("a", 11);
        try {
            nameId.inverse().put(111, "a"); // key已存在
        } catch (Exception e) {
            e.printStackTrace(); // IllegalArgumentException:value already present: a
        }
        nameId.inverse().forcePut(444, "c"); // 强制更新
        System.err.println(nameId); // {a=11, b=2, c=444}
        System.out.println(nameId.values()); // values返回Set<T>而不是Collection
    }

    private enum keys {
        K1, K2, K3
    }

    private enum values {
        V1, V2, V3
    }

    @Test
    public void otherImpl() {
        // ImmutableBiMap
        Map<String, Integer> temp = new HashMap<>();
        temp.put("cc", 33);
        BiMap<String, Integer> map = new ImmutableBiMap.Builder<String, Integer>().put("a", 1).put("b", 2).putAll(temp).build(); // 不可变
        System.out.println(map); // {a=1, b=2, cc=33} ImmutableBiMap不支持任何修改操作
        // EnumBiMap
        BiMap<keys, values> enumMap = EnumBiMap.create(keys.class, values.class);
        enumMap.put(keys.K1, values.V2);
        System.out.println(enumMap); // {K1=V2}
    }

    void initMap(BiMap<String, Integer> map) {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
    };

}
