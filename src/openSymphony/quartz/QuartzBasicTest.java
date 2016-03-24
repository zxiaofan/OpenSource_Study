/*
 * 文件名：QuartzBasic.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： QuartzBasic.java
 * 修改人：yunhai
 * 修改时间：2016年3月24日
 * 修改内容：新增
 */
package openSymphony.quartz;

/**
 * Quartz入门demo
 * 
 * @author yunhai
 */
public class QuartzBasicTest {
    /**
     * 定时器.
     */
    static String cronExpression = "0/5 * * * * ?"; // 每5秒执行一次

    /**
     * Junit无法执行.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        QuartzManager.addJob(null, "jobName", MyJob.class, null, cronExpression);
    }
}
