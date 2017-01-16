/*
 * 文件名：Collections2_Study.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： Collections2_Study.java
 * 修改人：zxiaofan
 * 修改时间：2017年1月16日
 * 修改内容：新增
 */
package guava.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import guava.model.UserBo;
import guava.util.GsonUtil;

/**
 * 
 * @author zxiaofan
 */
public class Collections2_Study {
    List<UserBo> listUser = Lists.newArrayList(null, new UserBo("nameA", 12), new UserBo("nameB", 16), new UserBo("nameAC", 19));

    List<String> listStr = Lists.newArrayList("a", "abc", "ab");

    /**
     * 谓语（限定name属性）.
     */
    Predicate<UserBo> predicateName = new Predicate<UserBo>() {

        @Override
        public boolean apply(UserBo input) {
            return null != input && input.getName().contains("A");
        }
    };

    /**
     * 谓语要注意判空.
     */
    Predicate<UserBo> predicateAge = new Predicate<UserBo>() {

        @Override
        public boolean apply(UserBo input) {
            return null != input && input.getAge() > 16;
        }
    };

    /**
     * filter返回的filterCollection仍然有predicate的特性（但返回值必须是Collection<T>且不能通过new ArrayList等手段创建新的实例）.
     * 
     * 如果新增不符合规则的数据到filterCollection，会抛异常IllegalArgumentException。
     * 
     */
    @Test
    public void filterTest() {
        Collection<UserBo> preBos = Collections2.filter(listUser, predicateName);
        Collection<UserBo> preBosNew = new ArrayList<>(Collections2.filter(listUser, predicateName)); // 新增数据不受规则限制
        System.out.println(GsonUtil.toJson(preBos)); // 筛选出符合predicateName规则的数据
        UserBo bo1 = new UserBo("nameD", 16);
        UserBo bo2 = new UserBo("nameAD", 16);
        // filter返回的 filterCollection仍然有predicate的特性
        // preBos.add(bo1); // IllegalArgumentException（bo1的name不符合规则）
        preBosNew.add(bo1);
        preBos.add(bo2);
        System.out.println(GsonUtil.toJson(preBosNew)); // 成功新增不符合规则的nameD
        System.out.println(GsonUtil.toJson(preBos)); // 新增nameAD
        // 两谓语and或or
        Collection<UserBo> preNameAge = Collections2.filter(listUser, Predicates.and(predicateName, predicateAge));
        System.out.println(GsonUtil.toJson(preNameAge)); // 仅输出nameAC(19)

    }

    /**
     * orderedPermutations排列（参数不能含null），产生n!个排列组合.
     * 
     */
    @Test
    public void orderedPermutationsTest() {
        Collection<List<String>> listOrderd = Collections2.orderedPermutations(listStr);
        System.out.println(GsonUtil.toJson(listOrderd)); // a>ab>abc
        Comparator<String> compare = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        };
        // 通过比较器定义排列顺序
        Collection<List<String>> listOrderd2 = Collections2.orderedPermutations(listStr, compare);
        System.out.println(GsonUtil.toJson(listOrderd2)); // abc>ab>a
    }

    /**
     * transform（对每个元素应用一个function）.
     * 
     * Function<UserBo, String>:<入参,返回值>
     */
    @Test
    public void transformTest() {
        Function<UserBo, String> function = new Function<UserBo, String>() {

            @Override
            public String apply(UserBo input) {
                if (null != input) {
                    return input.getName().replace("name", "");
                }
                return null;
            }
        };
        Collection<String> listTransform = Collections2.transform(listUser, function);
        System.out.println(GsonUtil.toJson(listTransform));
    }
}
