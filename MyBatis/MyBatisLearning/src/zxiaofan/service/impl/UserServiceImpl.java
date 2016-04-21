/*
 * 文件名：UserServiceImpl.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserServiceImpl.java
 * 修改人：yunhai
 * 修改时间：2016年4月21日
 * 修改内容：新增
 */
package zxiaofan.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import zxiaofan.dao.impl.GetInfoDao;
import zxiaofan.model.StudentVo;
import zxiaofan.service.IUserService;

/**
 * 
 * @author yunhai
 */
@Component("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private GetInfoDao getInfoDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public StudentVo getInfo(int id) {
        return getInfoDao.getInfo(id);
    }

}
