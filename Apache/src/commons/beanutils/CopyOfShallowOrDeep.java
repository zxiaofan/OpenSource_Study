/*
 * 文件名：CopyOfShallowOrDeep.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： CopyOfShallowOrDeep.java
 * 修改人：zxiaofan
 * 修改时间：2017年4月20日
 * 修改内容：新增
 */
package commons.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import model.BigStudentBo;
import model.Student;

/**
 * 深浅拷贝测试.
 * 
 * @author zxiaofan
 */
public class CopyOfShallowOrDeep {
    /**
     * PropertyUtils.copyProperties/BeanUtils.copyProperties均是浅拷贝（浅拷贝针对于拷贝对象内的嵌套对象）.
     * 
     * 如果拷贝的对象A内嵌套了对象C1，那么目标对象B引用的的依旧是C1而不是C2.
     * 
     */
    @Test
    public void testCopyShallowOrDeep() {
        // 单一对象拷贝
        Student student = new Student("src", 12);
        Student student2 = new Student();
        copy(student2, student);
        student.setName("src_new1");
        System.out.println(student.getName()); // src_new1
        System.out.println(student2.getName()); // src
        // 嵌套对象拷贝
        BigStudentBo src = new BigStudentBo(3, student);
        BigStudentBo dest = new BigStudentBo();
        copy(dest, src);
        src.getStudent().setName("src_new2");
        System.out.println(src.getStudent().getName()); // src_new2
        System.out.println(dest.getStudent().getName()); // src_new2
    }

    private void copy(Object dest, Object src) {
        try {
            // PropertyUtils.copyProperties(dest, src);
            BeanUtils.copyProperties(dest, src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
