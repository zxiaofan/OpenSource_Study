/*
 * 文件名：StartBusiness.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： StartBusiness.java
 * 修改人：yunhai
 * 修改时间：2016年3月24日
 * 修改内容：新增
 */
package openSymphony.quartz.listener;

import org.springframework.stereotype.Component;

import openSymphony.quartz.MyJob;
import openSymphony.quartz.QuartzManager;

/**
 * 
 * @author yunhai
 */
@Component
public class StartBusiness implements IStartBusiness {
    /**
     * 定时器.
     */
    static String cronExpression = "0/8 * * * * ?"; // 每8秒执行一次

    /**
     * {@inheritDoc}.
     */
    @Override
    public void startWork() throws Exception {
        QuartzManager.addJob(null, "jobName", MyJob.class, null, cronExpression);
    }

}
