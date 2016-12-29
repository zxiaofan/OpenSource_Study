/*
 * 文件名：RangeSet_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： RangeSet_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月27日
 * 修改内容：新增
 */
package guava.collect;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

/**
 * RangeSet<C extends Comparable>、RangeMap
 * 
 * @author zxiaofan
 */
public class Range_Study {
    @Test
    public void basicRangeSetTest() {
        RangeSet<Integer> range = TreeRangeSet.create();
        range.add(Range.closed(1, 10)); // [1..10]左右均闭合
        range.add(Range.closedOpen(12, 15)); // [12..15)左闭右开
        System.out.println(range); // [[1..10], [12..15)]
        System.out.println(Range.atLeast(2)); // x>=2
        System.out.println(Range.atMost(2)); // x<=2
        System.out.println(Range.greaterThan(2)); // x>2
        System.out.println(Range.lessThan(2)); // x<2
        System.out.println(Range.all()); // (-∞..+∞)

        // 视图
        System.out.println(range.contains(11)); // 元素属于集合 false
        System.out.println(range.subRangeSet(Range.closed(1, 5))); // 交集[[1..5]]
        System.out.println("...asRanges用于遍历");
        Set<Range<Integer>> asRange = range.asRanges();
        for (Range<Integer> ran : asRange) {
            System.out.println("asRanges:" + ran.toString());
        }
        System.out.println(range.complement()); // 补集 [(-∞..1), (10..12), [15..+∞)]
        System.out.println(range.rangeContaining(13)); // 元素属于Range的哪个范围内 [12..15)/null
        RangeSet<Integer> rr = TreeRangeSet.create();
        rr.add(Range.closed(0, 5));
        // RangeSet<Integer>和Range.closed(l,u)的区别：前者是多个集合[[1..10], [12..15)]，后者是单一集合[9..10]
        System.out.println(range.enclosesAll(rr)); // range集合包含rr false
        System.out.println(range.encloses(Range.closed(9, 10))); // range集合包含后者 true
        System.out.println(range.span()); // 返回包括RangeSet中所有区间的最小区间:[[1..10], [12..15)]--> [1..15)
    }

    /**
     * RangeMap描述了”不相交的、非空的区间”到特定值的映射。和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值.
     * 
     * put、remove即在原区间加入（覆盖）、移除部分区间。
     * 
     * RangeMap没有提供complement()、contains()、rangeContaining()以及encloses()方法,但支持交集subRangeMap。
     */
    @Test
    public void RangeMapTest() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(3, 7), "v2"); // [[3..7]=v2]
        System.out.println(rangeMap);
        Range<Integer> key1 = Range.closed(1, 5);
        rangeMap.put(key1, "v1"); // [[1..5]=v1, (5..7]=v2]
        System.out.println(rangeMap);
        rangeMap.put(Range.closed(9, 10), "v3");
        System.out.println(rangeMap); // [[1..5]=v1, (5..7]=v2, [9..10]=v3]
        rangeMap.remove(Range.closed(4, 6));
        System.out.println(rangeMap); // [[1..4)=v1, (6..7]=v2, [9..10]=v3]
        System.out.println(rangeMap.get(2)); // v1
        Map<Range<Integer>, String> map = rangeMap.asMapOfRanges(); // asMapOfRanges不可修改
        System.out.println(map.containsKey(Range.closedOpen(1, 4))); // true，当前key集合
        for (java.util.Map.Entry<Range<Integer>, String> entry : map.entrySet()) {
            System.out.println("entry:" + entry.getKey() + "," + entry.getValue());
        }
        System.out.println(rangeMap.subRangeMap(key1)); // {[1..4)=v1}
    }
}
