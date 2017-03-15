/*
 * 文件名：ConsumerServiceImpl.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ConsumerServiceImpl.java
 * 修改人：zxiaofan
 * 修改时间：2017年3月15日
 * 修改内容：新增
 */
package com.zxiaofan.dubboConsumer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zxiaofan.dubboConsumer.provider.IProviderService;
import com.zxiaofan.dubboConsumer.service.IConsumerService;

/**
 * 
 * @author zxiaofan
 */
@Component
public class ConsumerServiceImpl implements IConsumerService {
    @Resource(name = "providerService")
    private IProviderService providerService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String hi(String name) {
        String result = providerService.helloBoy(name);
        System.out.println("Consumer:" + result);
        return result;
    }

}
