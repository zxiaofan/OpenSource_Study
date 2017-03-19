/*
 * 文件名：userDo.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： userDo.java
 * 修改人：xiaofan
 * 修改时间：2017年3月19日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.model;

import java.util.Date;

/**
 * 用户表.
 * 
 * @author xiaofan
 */
public class UserDo {
    private String tableName;

    private Integer id;

    private String userName;

    private Integer age;

    private Date addTime;

    private Date modifyTime;

    private int isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "UserDo [tableName=" + tableName + ", id=" + id + ", userName=" + userName + ", age=" + age + ", addTime=" + addTime + ", modifyTime=" + modifyTime + ", isDelete=" + isDelete + "]";
    }

}
