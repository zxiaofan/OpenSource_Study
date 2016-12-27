/*
 * 文件名：RangeSet_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： RangeSet_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月27日
 * 修改内容：新增
 */
package guava.collect;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

/**
 * 
 * @author zxiaofan
 */
public class RangeSet_Study {
    @Test
    public void basicTest() {
        RangeSet<Integer> set = TreeRangeSet.create();
        set.add(Range.closed(1, 10));
        set.add(Range.closedOpen(12, 15));
        System.out.println(set);
    }
}
