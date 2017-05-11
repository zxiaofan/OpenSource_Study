/*
 * 文件名：InitApplicationListener.java
 * 版权：Copyright 2007-2017 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： InitApplicationListener.java
 * 修改人：zxiaofan
 * 修改时间：2017年5月11日
 * 修改内容：新增
 */
package com.zxiaofan.httpServer.listener;

import java.util.Objects;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Spring启动初始化（实现ApplicationListener接口）
 * 
 * @author zxiaofan
 */
@Component("initApplicationListener") // 必须交由Spring管理（或者<bean class="com.xx.xxx.InitApplicationListener"/>）
public class InitApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // web 项目中（spring mvc），系统会存在两个容器，一个是root application context ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）
        // WebApplicationContext for namespace 'HttpServer-servlet'
        if (event.getApplicationContext().getParent() == null && Objects.equals(event.getApplicationContext().getDisplayName(), "Root WebApplicationContext")) {// 避免重复初始化
            System.out.println("InitApplicationListener");
        }
    }

}
