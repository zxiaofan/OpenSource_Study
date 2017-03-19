/*
 * 文件名：UserServiceImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserServiceImpl.java
 * 修改人：xiaofan
 * 修改时间：2017年3月19日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zxiaofan.dubboProvidder.business.IUserBusiness;
import com.zxiaofan.dubboProvidder.model.UserDo;
import com.zxiaofan.dubboProvidder.service.IUserService;

/**
 * 
 * @author xiaofan
 */
@Component("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserBusiness userBusiness;

    /**
     * {@inheritDoc}.
     */
    @Override
    public int insert(UserDo userDo) {
        int result = userBusiness.insert(userDo);
        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public UserDo selectByID(UserDo userDo) {
        UserDo result = userBusiness.selectByID(userDo);
        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int update(UserDo userDo) {
        return userBusiness.update(userDo);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int delete(UserDo userDo) {
        return userBusiness.delete(userDo);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String selectByID(String id) {
        UserDo userDo = new UserDo();
        userDo.setTableName("user");
        userDo.setId(Integer.valueOf(id));
        UserDo result = userBusiness.selectByID(userDo);
        if (null != result) {
            return result.toString();
        } else {
            return "No Result!";
        }
    }

}
