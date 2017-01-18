/*
 * 文件名：MyPredicate.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MyPredicate.java
 * 修改人：yunhai
 * 修改时间：2015年12月2日
 * 修改内容：新增
 */
package commons.collections.CollectionUtils;

import org.apache.commons.collections.Predicate;

import model.Student;

/**
 * @author yunhai
 */
public class MyPredicate implements Predicate {
    private String name;

    private Integer age; // Integer 才能等于null，int默认初始值为0

    public MyPredicate(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean evaluate(Object object) {
        if (object instanceof Student) {
            Student resource = (Student) object;
            if (name == null) {
                if (resource.getAge() == age) {
                    return true;
                }
            } else if (age == null) {
                if (resource.getName() == name) {
                    return true;
                }
            } else {
                if (resource.getAge() == age && resource.getName() == name) {
                    return true;
                }
            }

        }
        return false;
    }
}
