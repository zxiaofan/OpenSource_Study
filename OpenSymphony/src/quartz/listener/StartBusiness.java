/*
 * 文件名：StartBusiness.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： StartBusiness.java
 * 修改人：yunhai
 * 修改时间：2016年3月24日
 * 修改内容：新增
 */
package quartz.listener;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

import quartz.MyJob;
import quartz.QuartzManager;

/**
 * 
 * @author yunhai
 */
@Component
public class StartBusiness implements IStartBusiness {
    @Resource(name = "scheduler")
    public Scheduler scheduler;

    /**
     * 定时器.
     */
    static String cronExpression = "0/5 * * * * ?"; // 每5秒执行一次

    /**
     * {@inheritDoc}.
     */
    @Override
    public void startWork() throws Exception {
        // QuartzManager.addJob(null, "jobName", MyJob.class, null, cronExpression);
        //
        // spring注入Scheduler，单例
        QuartzManager.addJob(scheduler, MyJob.class, cronExpression);
    }

}