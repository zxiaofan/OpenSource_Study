/*
 * 文件名：MyJob.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MyJob.java
 * 修改人：yunhai
 * 修改时间：2016年3月24日
 * 修改内容：新增
 */
package openSymphony.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author yunhai
 */
public class MyJob implements Job {
    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    /**
     * {@inheritDoc}.
     */
    @Override
    public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        System.out.println("execute myJob:" + sdf.format(new Date()));
    }

}
