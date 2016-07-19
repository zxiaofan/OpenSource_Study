/*
 * 文件名：IUserService.java
 * 版权：Copyright 2007-2016 zxiaofan.com. Co. Ltd. All Rights Reserved. 
 * 描述： IUserService.java
 * 修改人：yunhai
 * 修改时间：2016年4月21日
 * 修改内容：新增
 */
package zxiaofan.service;

import zxiaofan.model.StudentVo;

/**
 * 
 * @author yunhai
 */
public interface IUserService {
    public StudentVo getInfo(int id);
}
