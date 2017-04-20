/*
 * 文件名：Student.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： Student.java
 * 修改人：yunhai
 * 修改时间：2015年12月2日
 * 修改内容：新增
 */
package model;

import java.util.Date;

/**
 * module.
 * 
 * @author yunhai
 */
public class Student {
    private String name;

    private int age;

    private double grade;

    private Date date;

    public Student() {
        super();
    }

    public Student(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    /**
     * 构造函数.
     * 
     */
    public Student(String name, int age, Date date) {
        super();
        this.name = name;
        this.age = age;
        this.date = date;
    }

    public Student(String name, int age, double grade) {
        this(name, age);
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", grade=" + grade + ", date=" + date + "]";
    }

}
