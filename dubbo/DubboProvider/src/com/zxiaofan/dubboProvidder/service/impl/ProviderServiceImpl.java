/*
 * 文件名：ProviderServiceImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ProviderServiceImpl.java
 * 修改人：xiaofan
 * 修改时间：2017年3月13日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zxiaofan.dubboProvidder.model.HelloBo;
import com.zxiaofan.dubboProvidder.service.IProviderService;

/**
 * 
 * @author xiaofan
 */
@Component("providerService")
public class ProviderServiceImpl implements IProviderService {
    @Value("${param.url}")
    private String url;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String helloBoy(String name) {
        String result = "hello " + name + ", This is dubboProvider[" + url + "]";
        System.out.println(result);
        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public HelloBo helloGirl(HelloBo helloBo) {
        if (null == helloBo.getName()) {
            helloBo.setName("DefaultName");
        }
        helloBo.setUrl(helloBo.getName() + ":This is dubboProvider[csdn.zxiaofan.com]");
        return helloBo;
    }

}
