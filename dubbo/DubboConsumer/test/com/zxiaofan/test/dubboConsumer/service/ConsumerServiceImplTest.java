/*
 * 文件名：ConsumerServiceImplTest.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： ConsumerServiceImplTest.java
 * 修改人：zxiaofan
 * 修改时间：2017年3月15日
 * 修改内容：新增
 */
package com.zxiaofan.test.dubboConsumer.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxiaofan.dubboConsumer.service.IConsumerService;
import com.zxiaofan.test.dubboConsumer.BaseTest;

/**
 * 
 * @author zxiaofan
 */
public class ConsumerServiceImplTest extends BaseTest {
    @Autowired
    private IConsumerService consumerService;

    /**
     * Test method for {@link com.zxiaofan.dubboConsumer.service.impl.ConsumerServiceImpl#hi(java.lang.String)}.
     */
    @Test
    public void testHi() {
        String name = "zxiaofan";
        String result = consumerService.hi(name);
        System.out.println("testResult:" + result);
    }

}
