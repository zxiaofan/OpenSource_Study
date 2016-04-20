/*
 * 文件名：StudentVo.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： StudentVo.java
 * 修改人：yunhai
 * 修改时间：2016年4月20日
 * 修改内容：新增
 */
package model;

/**
 * 
 * @author yunhai
 */
public class StudentVo {
    private int id;

    private String name;

    private int age;

    /**
     * 设置id.
     * 
     * @return 返回id
     */
    public int getId() {
        return id;
    }

    /**
     * 获取id.
     * 
     * @param id
     *            要设置的id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 设置name.
     * 
     * @return 返回name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取name.
     * 
     * @param name
     *            要设置的name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置age.
     * 
     * @return 返回age
     */
    public int getAge() {
        return age;
    }

    /**
     * 获取age.
     * 
     * @param age
     *            要设置的age
     */
    public void setAge(int age) {
        this.age = age;
    }
}
