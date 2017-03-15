/*
 * 文件名：ProviderServiceImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ProviderServiceImpl.java
 * 修改人：xiaofan
 * 修改时间：2017年3月13日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.service.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.zxiaofan.dubboProvidder.service.IProviderService;

/**
 * 
 * @author xiaofan
 */
@Component("providerService")
 public class ProviderServiceImpl implements IProviderService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public String helloBoy(String name) {
        String result = "hello " + name + new Date().toString();
        System.out.println(result);
        return result;
    }

}
