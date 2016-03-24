/*
 * 文件名：IPullBusiness.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： IPullBusiness.java
 * 修改人：tashan
 * 修改时间：2015年4月14日
 * 修改内容：新增
 */
package openSymphony.quartz.listener;

/**
 * 执行接口
 */
public interface IStartBusiness {
    /**
     * 工作开始.
     * 
     * 行线列表
     * 
     * @throws Exception
     *             Exception
     */
    public void startWork() throws Exception;
}
