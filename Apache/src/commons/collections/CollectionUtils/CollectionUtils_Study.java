/*
 * 文件名：CollectionUtils_Study.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CollectionUtils_Study.java
 * 修改人：yunhai
 * 修改时间：2015年12月2日
 * 修改内容：新增
 */
package commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Test;

import model.Student;

/**
 * 从一个List中查找符合一定条件的对象.
 * 
 * @author yunhai
 */
public class CollectionUtils_Study {
    @SuppressWarnings("unchecked")
    @Test
    public void CollectionUtilsTest() {
        List<Student> listStu = new ArrayList<Student>();
        List<Student> newStu = new ArrayList<Student>();
        List<Student> newStu2 = new ArrayList<Student>();
        listStu.add(new Student("A", 20));
        listStu.add(new Student("B", 30));
        listStu.add(new Student("C", 25));
        listStu.add(new Student("D", 20));
        // age=20
        newStu = (List<Student>) CollectionUtils.select(listStu, new Predicate() {
            public boolean evaluate(Object arg0) {
                Student u = (Student) arg0;
                return 20 == u.getAge();
            }
        });
        newStu.forEach(stu -> System.out.print(stu.getName() + " "));
        System.out.println();
        // Predicate代码复用
        // age=30
        newStu2 = (List<Student>) CollectionUtils.select(listStu, new MyPredicate(null, 30));
        newStu2.forEach(stu -> System.out.print(stu.getName() + " "));
        System.out.println();
        // name=A
        newStu2 = (List<Student>) CollectionUtils.select(listStu, new MyPredicate("A", null));
        newStu2.forEach(stu -> System.out.print(stu.getName() + " "));
        System.out.println();
        // name=A,age=20
        newStu2 = (List<Student>) CollectionUtils.select(listStu, new MyPredicate("A", 20));
        newStu2.forEach(stu -> System.out.print(stu.getName() + " "));
    }
}
