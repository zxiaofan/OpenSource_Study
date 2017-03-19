/*
 * 文件名：BaseTest.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： BaseTest.java
 * 修改人：zxiaofan
 * 修改时间：2017年3月15日
 * 修改内容：新增
 */
package com.zxiaofan.test.dubboProvider;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zxiaofan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/com/zxiaofan/test/config/app-context.xml")
public class BaseTest {
    // protected Logger logger = Logger.getLogger(BaseTest.class);
    static {
        // try {
        // // Log4jConfigurer.initLogging("WebContent/WEB-INF/log4j.properties"); // spring3.2.3可用，高版本4.3.7不推荐使用
        // } catch (FileNotFoundException ex) {
        // System.err.println("Cannot Initialize log4j");
        // }
        System.out.println("SpringIOC...");
    }
}
