/*
 * 文件名：BaseTest.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BaseTest.java
 * 修改人：zxiaofan
 * 修改时间：2017年3月15日
 * 修改内容：新增
 */
package com.zxiaofan.test.dubboConsumer;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zxiaofan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/com/zxiaofan/test/config/app-context.xml")
public class BaseTest {
    static {
        System.out.println("SpringIOC...");
    }
}
