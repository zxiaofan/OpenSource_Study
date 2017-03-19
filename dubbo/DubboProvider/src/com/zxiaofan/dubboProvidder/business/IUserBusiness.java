/*
 * 文件名：IUserBusiness.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： IUserBusiness.java
 * 修改人：xiaofan
 * 修改时间：2017年3月19日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.business;

import com.zxiaofan.dubboProvidder.model.UserDo;

/**
 * 
 * @author xiaofan
 */
public interface IUserBusiness {
    int insert(UserDo userDo);

    UserDo selectByID(UserDo userDo);

    int update(UserDo userDo);

    int delete(UserDo userDo);
}
