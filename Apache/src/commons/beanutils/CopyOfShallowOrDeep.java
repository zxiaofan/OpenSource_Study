/*
 * 文件名：CopyOfShallowOrDeep.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： CopyOfShallowOrDeep.java
 * 修改人：zxiaofan
 * 修改时间：2017年4月20日
 * 修改内容：新增
 */
package commons.beanutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import model.BigStudentBo;
import model.Student;

/**
 * 深浅拷贝测试.
 * 
 * @author zxiaofan
 */
public class CopyOfShallowOrDeep {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    /**
     * PropertyUtils.copyProperties/BeanUtils.copyProperties均是浅拷贝（浅拷贝针对于拷贝对象内的嵌套对象）.
     * 
     * A(C1)-->B：如果拷贝的对象A内嵌套了对象C1，那么目标对象B引用的的依旧是C1而不是C2.
     * 
     * A直接属性（如Date）经copyProperties拷贝到B属于深拷贝，A中对象C拷贝到B属于浅拷贝。
     * 
     * @throws ParseException
     * 
     */
    @Test
    public void testCopyShallowOrDeep() throws ParseException {
        // 单一对象拷贝
        Student student = new Student("src", 12, format.parse("20170420"));
        Student student2 = new Student();
        copy(student2, student);
        student.setName("src_new1");
        student.setDate(format.parse("20170419")); // 测试非基本类型（依旧深拷贝）
        System.out.println(student.toString()); // src_new1,20170419
        System.out.println(student2.toString()); // src,20170420
        // 嵌套对象拷贝
        BigStudentBo src = new BigStudentBo(3, student);
        BigStudentBo dest = new BigStudentBo();
        copy(dest, src);
        src.getStudent().setName("src_new2");
        src.getStudent().setDate(format.parse("20170308")); // 修改引用对象的值
        System.out.println(src.getStudent().toString()); // src_new2,20170308
        System.out.println(dest.getStudent().toString()); // src_new2,20170308
    }

    private void copy(Object dest, Object src) {
        try {
            PropertyUtils.copyProperties(dest, src);
            // BeanUtils.copyProperties(dest, src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
