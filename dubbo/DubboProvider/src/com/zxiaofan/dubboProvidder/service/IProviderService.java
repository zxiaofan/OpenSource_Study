/*
 * 文件名：IProviderService.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： IProviderService.java
 * 修改人：xiaofan
 * 修改时间：2017年3月13日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.service;

import com.zxiaofan.dubboProvidder.model.HelloBo;

/**
 * 
 * @author xiaofan
 */
public interface IProviderService {
    /**
     * 测试字符串传输.
     * 
     * @param name
     * @return
     */
    String helloBoy(String name);

    /**
     * 测试bean传输.
     * 
     * @param bo
     * @return
     */
    HelloBo helloGirl(HelloBo bo);
}
