/*
 * 文件名：UserServiceImplTest.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserServiceImplTest.java
 * 修改人：xiaofan
 * 修改时间：2017年3月19日
 * 修改内容：新增
 */
package com.zxiaofan.test.dubboProvider.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.zxiaofan.dubboProvidder.model.UserDo;
import com.zxiaofan.dubboProvidder.service.IUserService;
import com.zxiaofan.test.dubboProvider.BaseTest;

/**
 * @author xiaofan
 */
public class UserServiceImplTest extends BaseTest {
    @Resource(name = "userService")
    private IUserService userService;

    @Test
    public void testInsert() {
        UserDo userDo = new UserDo();
        userDo.setTableName("user");
        userDo.setUserName("zxiaofan");
        userDo.setAge(18);
        userDo.setAddTime(new Date());
        userDo.setIsDelete(0);
        int result = userService.insert(userDo);
        System.out.println(1 == result ? "success" : "false");
    }

    @Test
    public void testQueryByID() {
        UserDo userDo = new UserDo();
        userDo.setTableName("user");
        userDo.setId(4);
        UserDo result = userService.selectByID(userDo);
        if (null != result) {
            System.out.println(result.getId() + "," + result.getUserName() + "," + result.getAddTime().toString());
        } else {
            System.out.println("No Result");
        }
    }

    @Test
    public void testUpdateByID() {
        UserDo userDo = new UserDo();
        userDo.setTableName("user");
        userDo.setUserName("name-New");
        userDo.setId(4);
        int result = userService.update(userDo);
        System.out.println(1 == result ? "success" : "false");
    }

    @Test
    public void testDeleteByID() {
        UserDo userDo = new UserDo();
        userDo.setTableName("user");
        userDo.setUserName("name-New");
        int result = userService.delete(userDo);
        System.out.println(1 == result ? "success" : "false");
    }

}
