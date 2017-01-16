/*
 * 文件名：ClassToInstanceMap_Study.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ClassToInstanceMap_Study.java
 * 修改人：zxiaofan
 * 修改时间：2016年12月27日
 * 修改内容：新增
 */
package guava.collect;

import org.junit.Test;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

import guava.model.UserBo;

/**
 * map的key并不是一种类型，是很多类型（常规Map的key、value类型固定），可通过类型得到其实例；
 * 
 * 保证放入的value和key的类型对应，不一致将报转换异常。
 * 
 * 实现：MutableClassToInstanceMap、ImmutableClassToInstanceMap
 * 
 * @author zxiaofan
 */
public class ClassToInstanceMap_Study {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void basicTest() {
        // MutableClassToInstanceMap
        ClassToInstanceMap map = MutableClassToInstanceMap.create();
        UserBo user = new UserBo();
        user.setName("n1");
        map.put(UserBo.class, user);
        map.put(String.class, "str");
        map.put(String.class, "str2");
        map.putInstance(Integer.class, 1);
        UserBo bo = (UserBo) map.getInstance(UserBo.class);
        System.out.println(bo.getName()); // n1
        System.out.println(map.getInstance(String.class)); // str2
        // ImmutableClassToInstanceMap
        ClassToInstanceMap immuMap = new ImmutableClassToInstanceMap.Builder<Number>().put(Integer.class, 1).put(Double.class, 2.2).build();
        System.out.println(immuMap.toString()); // {class java.lang.Integer=1, class java.lang.Double=2.2}
    }
}
