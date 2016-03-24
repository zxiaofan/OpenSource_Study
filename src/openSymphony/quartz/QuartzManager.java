/*
 * 文件名：QuartzManager.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： QuartzManager.java
 * 修改人：yunhai
 * 修改时间：2016年3月24日
 * 修改内容：新增
 */
package openSymphony.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * 任务管理
 * 
 * @author yunhai
 */
public class QuartzManager {
    private static SchedulerFactory sf = new StdSchedulerFactory();

    private static String JOB_GROUP_NAME = "group_name_default";

    private static String TRIGGER_GROUP_NAME = "trigger_name_default";

    private static JobDataMap jobDataMapDefault = new JobDataMap();

    /**
     * 构造函数.
     * 
     */
    private QuartzManager() {
        throw new RuntimeException("This is util class,can not instance!");
    }

    /**
     * 添加任务.
     * 
     * @param scheduler
     *            定时容器
     * @param jobClass
     *            任务的类型
     * @param cronExpression
     *            启动时间
     * @throws Exception
     *             e
     */
    public static void addJob(Scheduler scheduler, Class<? extends Job> jobClass, String cronExpression) throws Exception {
        if (null == jobClass || null == cronExpression || cronExpression.trim().length() == 0) {
            throw new RuntimeException("scheduler/jobName/jobClass/cronExpression/jobDataMap/nextFireTime can not null");
        }
        if (null == scheduler) {
            scheduler = sf.getScheduler();
        }
        String jobName = JOB_GROUP_NAME;
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(jobName);
        jobDetail.setGroup(jobName);
        jobDetail.setJobClass(jobClass);
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setCronExpression(cronExpression);
        trigger.setName(jobName);
        trigger.setGroup(jobName);
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    /**
     * 添加任务.
     * 
     * @param scheduler
     *            定时容器
     * @param jobName
     *            任务名称
     * @param jobClass
     *            任务的类型
     * @param jobDataMap
     *            任务参数map
     * @param cronExpression
     *            启动时间
     * @throws Exception
     *             e
     */
    public static void addJob(Scheduler scheduler, String jobName, Class<? extends Job> jobClass, JobDataMap jobDataMap, String cronExpression) throws Exception {
        if (null == jobClass || null == cronExpression || cronExpression.trim().length() == 0) {
            throw new RuntimeException("scheduler/jobName/jobClass/cronExpression/jobDataMap/nextFireTime can not null");
        }
        if (null == scheduler) { // 不建议如此使用，会new 多个Scheduler，建议Scheduler不能为空，用Spring单例注入传入此参数
            scheduler = sf.getScheduler();
        }
        if (null == jobName || jobName.trim().length() == 0) {
            jobName = JOB_GROUP_NAME;
        }
        if (null == jobDataMap) {
            jobDataMap = jobDataMapDefault;
        }
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(jobName);
        jobDetail.setGroup(jobName);
        jobDetail.setJobClass(jobClass);
        jobDetail.setJobDataMap(jobDataMap);
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setJobDataMap(jobDataMap);
        trigger.setCronExpression(cronExpression);
        trigger.setName(jobName);
        trigger.setGroup(jobName);
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    /**
     * 修改任务触发时间规则.
     * 
     * @param scheduler
     *            定时容器
     * @param jobName
     *            任务名称
     * @param jobDataMap
     *            任务参数map
     * @param cronExpression
     *            下次启动时间
     * @throws Exception
     *             e
     */
    public static void modifyTrigger(Scheduler scheduler, String jobName, JobDataMap jobDataMap, String cronExpression) throws Exception {
        if (null == scheduler || null == jobName || jobName.trim().length() == 0 || jobDataMap == null) {
            throw new RuntimeException("scheduler/jobName/cronExpression/jobDataMap/nextFireTime can not null");
        }
        TriggerKey key = new TriggerKey(jobName, jobName);
        // Trigger trigger = scheduler.getTrigger(key);
        // CronTriggerImpl t = (CronTriggerImpl) trigger;
        // t.setCronExpression(cronExpression);
        // t.setJobDataMap(jobDataMap);
        // scheduler.rescheduleJob(key, t);

        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setJobDataMap(jobDataMap);
        trigger.setCronExpression(cronExpression);
        trigger.setName(jobName);
        trigger.setGroup(jobName);
        scheduler.rescheduleJob(key, trigger);

        // scheduler.pauseTrigger(key);
        // scheduler.resumeTrigger(key);
        // TriggerKey key = new TriggerKey(jobName, jobName);
        // Trigger trigger = scheduler.getTrigger(key);
        // CronTriggerImpl t = (CronTriggerImpl) trigger;
        // t.setNextFireTime(nextFireTime);
        // t.setJobDataMap(jobDataMap);
        // scheduler.rescheduleJob(key, t);
    }
}
