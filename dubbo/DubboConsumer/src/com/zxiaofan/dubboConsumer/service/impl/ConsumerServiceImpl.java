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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zxiaofan.dubboConsumer.service.IConsumerService;
import com.zxiaofan.dubboProvidder.model.HelloBo;
import com.zxiaofan.dubboProvidder.service.IProviderService;
import com.zxiaofan.dubboProvidder.service.IUserService;

/**
 * 
 * @author zxiaofan
 */
@Component("consumerService")
public class ConsumerServiceImpl implements IConsumerService {
    @Resource(name = "providerService")
    private IProviderService providerService;

    @Resource(name = "userService")
    private IUserService userService;

    @Value("${param.url}")
    private String url;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String hi(String name) {
        String result = null;
        if (null != name && name.startsWith("boy")) {
            System.out.println("Hi Boy!");
            result = providerService.helloBoy(name.replace("boy", "~~~"));
        } else if (null != name && name.startsWith("select")) {
            System.out.println("hi select!");
            result = userService.selectByID(name.replace("select", ""));
            return result;
        } else {
            HelloBo helloBo = new HelloBo();
            helloBo.setName(name);
            HelloBo helloBoResult = null;
            helloBoResult = providerService.helloGirl(helloBo);
            if (null != helloBoResult) {
                result = helloBoResult.getUrl();
            }
        }
        result += "; This is dubboConsumer[" + url + "]";
        return result;
    }

}
