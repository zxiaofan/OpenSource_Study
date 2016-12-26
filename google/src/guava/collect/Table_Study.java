/*
 * 文件名：Table_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Table_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月26日
 * 修改内容：新增
 */
package guava.collect;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * 
 * @author zxiaofan
 */
// HashBasedTable--HashMap<R, HashMap<C, V>>
// TreeBasedTable--TreeMap<R, TreeMap<C,V>>
// ImmutableTable--ImmutableMap<R, ImmutableMap<C, V>>ImmutableTable对稀疏或密集的数据集都有优化
// ArrayTable--二维数组，以提升访问速度和密集Table的内存利用率
public class Table_Study {
    @Test
    public void basicTest() {
        Table<String, String, Integer> table = HashBasedTable.create();
        initTable(table);
        System.out.println(table.rowMap()); // {r1={c1=11, c2=12}, r2={c2=22}}
        System.out.println(table.toString()); // <==>rowMap().toString()
        System.out.println(table.row("r1")); // {c1=11, c2=12}
        System.out.println(table.column("c2")); // {r1=12, r2=22}
        table.put(null, null, null); // java.lang.NullPointerException,3个值均不能为null
    }

    @Test
    public void otherImplTest() {
        // TreeBasedTable
        Table<String, String, Integer> table = TreeBasedTable.create();
        initTable(table);
        System.out.println(table.rowMap()); // {r1={c1=11, c2=12}, r2={c2=22}}
        table.put("r2", "c5", 21);
        table.put("r2", "c0", 21);
        System.out.println(table.rowMap()); // {r1={c1=11, c2=12}, r2={c0=21, c2=22, c5=21}}
        // ArrayTable 与其他Table的原理略有不同
        List<Integer> rowList = Lists.newArrayList(); // 确定行key
        rowList.add(1);
        rowList.add(2);
        List<Integer> columnList = Lists.newArrayList(); // 确定列key
        columnList.add(3);
        columnList.add(4);
        Table<Integer, Integer, String> table2 = ArrayTable.create(rowList, columnList); // 初始化ArrayTable时即确定其行key、列key的取值范围。
        table2.put(1, 3, "a");
        try {
            table2.clear(); // Table接口中的clear及remove方法不可使用
        } catch (Exception e1) {
            e1.printStackTrace();// UnsupportedOperationException
        }
        try {
            table2.remove(1, 3);
        } catch (Exception e1) {
            e1.printStackTrace(); // UnsupportedOperationException
        }
        try {
            table2.put(1, 1, "a");
        } catch (Exception e) {
            e.printStackTrace(); // java.lang.IllegalArgumentException: Column 1 not in [3, 4]
        }

    }

    private void initTable(Table<String, String, Integer> table) {
        table.put("r1", "c1", 11);
        table.put("r1", "c2", 12);
        table.put("r2", "c2", 22);
    }
}
