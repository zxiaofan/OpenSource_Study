/*
 * 文件名：Multiset_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Multiset_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月19日
 * 修改内容：新增
 */
package guava.collect;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Multiset继承自JDK中的Collection接口，而不是Set接口，所以包含重复元素并没有违反原有的接口契约。
 * 
 * Multiset不是一个Map<E,Integer>,本质上是一个Set加一个元素计数器.
 * 
 * Multiset中的元素的重复个数只会是正数，且最大不会超过Integer.MAX_VALUE。设定计数为0的元素将不会出现multiset中，也不会出现elementSet()和entrySet()的返回结果中。
 * 
 * multiset.iterator() 会循环迭代每一个出现的元素，迭代的次数与multiset.size()相同。
 * 
 * Map-Multiset对应关系-是否支持null：
 * 
 * HashMap-HashMultiset-Yes；TreeMap-TreeMultiset-Yes (if the comparator does)；LinkedHashMap-LinkedHashMultiset-Yes
 * 
 * ConcurrentHashMap-ConcurrentHashMultiset-No；ImmutableMap-ImmutableMultiset-No
 * 
 * @author zxiaofan
 */
public class Multiset_Study {
    /**
     * 允许重复，但是不保证顺序.
     * 
     * 底层结构HashMap<E, Count>()，Multiset遍历时可以遍历出Map.keySize * count个元素，而map却不可以，itertator和Entry<T,Count>的iterator实现。
     */
    @Test
    public void HashMultisetTest() {
        Multiset<String> list = HashMultiset.create();
        list.add("a");
        list.add("c");
        list.add("c");
        Collection<? extends String> tempList = Arrays.asList("a", "b");
        list.addAll(tempList); // 添加一个集合
        System.out.println(list); // [a x 2, b, c x 2]
        System.out.println(list.count("a")); // 2（如果该元素不在该集中，那么返回的结果只会是0）
        list.remove("a"); // 移除一个a
        list.remove("c", 1); // 移除一个c
        System.out.println(list); // [a, b, c]
        list.setCount("a", 3); // 设定a的个数为3
        System.out.println(list); // [a x 3, b, c]
        list.setCount("b", 1, 2); // 若b的重复个数为1则将其重复个数置为2
        System.out.println(list); // [a x 3, b x 2, c]
        list.retainAll(Arrays.asList("a", "b")); // 保留出现在指定集合中的元素
        System.out.println(list); // [a x 3, b x 2]
        list.removeAll(Arrays.asList("a")); // 去除指定集合中的所有元素
        System.out.println(list); // [b x 2]
        list.add(null); // HashMultiset--HashMap 支持null
        System.out.println(list); // [null, b x 2]
    }

    /**
     * 底层结构为ConcurrentHashMap<E, AtomicInteger>().
     * 
     */
    @Test
    public void ConcurrentHashMulisetTest() {
        Multiset<String> con = ConcurrentHashMultiset.create();
        con.add("a");
        con.add("b", 2); // ConcurrentMap.putIfAbsent(element, new AtomicInteger(occurrences))
        System.out.println(con);
    }

}
