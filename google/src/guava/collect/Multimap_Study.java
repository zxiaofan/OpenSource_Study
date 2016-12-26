/*
 * 文件名：Multimap_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Multimap_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月22日
 * 修改内容：新增
 */
package guava.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/**
 * Multimap:get(key)==>Collection<>（可能为空集合（默认容量为3）但不会为null，asMap.get()可能返回null）
 * 
 * 常使用ListMultimap或SetMultimap。
 * 
 * @author zxiaofan
 */
// Multimap实现--key类似--value类似
// ArrayListMultimap HashMap ArrayList
// HashMultimap HashMap HashSet
// LinkedListMultimap* LinkedHashMap* LinkedList*
// LinkedHashMultimap** LinkedHashMap LinkedHashMap
// TreeMultimap TreeMap TreeSet
// ImmutableListMultimap ImmutableMap ImmutableList
// ImmutableSetMultimap ImmutableMap ImmutableSet
// 除了两个不可变形式的实现，其他所有实现都支持null键和null值;
// LinkedListMultimap.entries()保留了所有键和值的迭代顺序;
// LinkedHashMultimap保留了映射项的插入顺序，包括键插入的顺序，以及键映射的所有值的插入顺序.
public class Multimap_Study {
    /**
     * 基本使用.
     * 
     */
    @Test
    public void basicTest() {
        Multimap<String, String> map = ArrayListMultimap.create(); // 类似于Map<String,List<String>>，不需要检查List中的对象是否存在
        map.put("a", "a1"); // ==>multimap.get(key).add(value)
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("b", "b1");
        multimap.put("b", "b2");
        map.putAll(multimap); // ==>Iterables.addAll(multimap.get(key), values)
        System.out.println(map.toString()); // {a=[a1], b=[b1, b2]}
        map.remove("b", "b1"); // ==>multimap.get(key).remove(value);移除指定key的指定value
        System.out.println(map.toString()); // {a=[a1], b=[b2]}
        map.removeAll("b"); // ==>multimap.get(key).clear();移除key对应的所有value
        System.out.println(map.get("c")); // []，该空集合容量expectedValuesPerKey为默认值DEFAULT_VALUES_PER_KEY= 3
        System.out.println(map.replaceValues("a", Arrays.asList("aa"))); // 返回旧值[a1]
        // replaceValues==>multimap.get(key).clear();
        // Iterables.addAll(multimap.get(key), values)
        System.out.println(map);
        System.out.println(map.containsKey("a")); // true
        System.out.println(map.containsValue("aa")); // contains方法包含key、value、entry
        System.out.println(map.containsEntry("a", "aa"));
    }

    /**
     * Multimap强大的视图.
     * 
     * 暂仅发现asMap.get()返回集合支持转换为List<>，其余视图转换时均报ClassCastException
     */
    @Test
    public void viewTest() {
        //
        // asMap
        //
        System.out.println("......asMap......");
        Multimap<String, String> map = initMap();
        map.asMap().remove("a"); // asMap支持remove并会反映到底层的Multimap
        System.out.println(map.toString()); // a被移除
        List<String> list = (List<String>) map.asMap().get("c");
        System.out.println(list); // asMap.get()可能返回null，而不是新的、可写的空集合
        //
        // entries
        //
        System.out.println("......entries......");
        map = initMap();
        // entries不能转换为ListEntry<,>>，ClassCastException
        Collection<Entry<String, String>> keyValue = map.entries(); // 返回Multimap中所有”键-单个值映射”——包括重复键，（对SetMultimap，返回的是Set）
        for (Entry<String, String> entry : keyValue) {
            System.out.println(entry.getKey() + "=" + entry.getValue()); // a=a1; a=a2;b=b1
        }
        System.out.println(map.toString()); // {a=[a1, a2], b=[b1]}
        //
        // keySet
        //
        System.out.println("......keySet......");
        System.out.println(map.keySet().toString()); // Multimap中所有不同的键 [a, b]
        //
        // keys
        //
        System.out.println("......keys......");
        Multiset<String> keys = map.keys(); // 返回Multiset表示Multimap中的所有键，键重复次数等于其映射的value的个数
        System.out.println(keys); // [a x 2, b]
        keys.remove("b"); // 可以从map.keys()移除，但不能add，移除会反射到底层的Multimap
        System.out.println(map.toString()); // {a=[a1, a2]}
        //
        // values
        //
        System.out.println("......values......");
        map = initMap();
        map.put("c", "a1");
        Collection<String> values = map.values(); // 返回单个Collection，包含所有value（可能重复）
        for (String value : values) {
            System.out.println(value); // a1;a2;b1;a1
        }
        //
        System.out.println(map.toString());
        // map.size()=map.keys().size()：key-value键值对的个数；map.keySet().size()：不同key的个数
        System.out.println("map.size():" + map.size() + ",map.keys().size():" + map.keys().size() + ",map.keySet().size():" + map.keySet().size());
    }

    /**
     * LinkedHashMultimap保留了映射项的插入顺序，包括键插入的顺序，以及键映射的所有值的插入顺序.
     * 
     */
    @Test
    public void LinkedHashMultimapTest() {
        Multimap<String, String> map = LinkedHashMultimap.create();
        map.put("c", "c2");
        map.put("c", "c1");
        map.put("a", "a1");
        map.put("d", "d1");
        System.out.println(map.toString()); // {c=[c2, c1], a=[a1], d=[d1]}
    }

    private Multimap<String, String> initMap() {
        System.out.println("初始化map...");
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("a", "a1");
        map.put("a", "a2");
        map.put("b", "b1");
        return map;
    }
}
