/*
 * 文件名：BigStudentBo.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BigStudentBo.java
 * 修改人：zxiaofan
 * 修改时间：2017年4月20日
 * 修改内容：新增
 */
package model;

/**
 * 
 * @author zxiaofan
 */
public class BigStudentBo {
    private int code;

    private Student student;

    public BigStudentBo() {
        super();
    }

    public BigStudentBo(int code, Student student) {
        super();
        this.code = code;
        this.student = student;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
