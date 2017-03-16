/*
 * 文件名：HelloBo.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： HelloBo.java
 * 修改人：zxiaofan
 * 修改时间：2017年3月16日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.model;

/**
 * 必须实现java.io.Serializable接口
 * 
 * @author zxiaofan
 */
public class HelloBo implements java.io.Serializable {
    private String name;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
