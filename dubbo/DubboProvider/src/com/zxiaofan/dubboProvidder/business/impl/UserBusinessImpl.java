/*
 * 文件名：UserBusinessImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserBusinessImpl.java
 * 修改人：xiaofan
 * 修改时间：2017年3月19日
 * 修改内容：新增
 */
package com.zxiaofan.dubboProvidder.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zxiaofan.dubboProvidder.business.IUserBusiness;
import com.zxiaofan.dubboProvidder.model.UserDo;
import com.zxiaofan.dubboProvidder.rowMapper.LocalRowMapper;

/**
 * 
 * @author xiaofan
 */
@Component
public class UserBusinessImpl implements IUserBusiness {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}.
     */
    @Override
    public int insert(UserDo userDo) {
        String sqlStr = "insert into " + userDo.getTableName() + " (userName,age,addTime,isDelete) values(?,?,?,?)";
        int result = jdbcTemplate.update(sqlStr, userDo.getUserName(), userDo.getAge(), userDo.getAddTime(), userDo.getIsDelete());
        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public UserDo selectByID(UserDo userDo) {
        String sqlStr = "select * from " + userDo.getTableName() + " where id=?";
        List<UserDo> dos = jdbcTemplate.query(sqlStr, new LocalRowMapper(UserDo.class), userDo.getId());
        if (null != dos && !dos.isEmpty()) {
            return dos.get(0);
        }
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int update(UserDo userDo) {
        String sqlStr = "update " + userDo.getTableName() + " set userName=? where id=?";
        int result = jdbcTemplate.update(sqlStr, userDo.getUserName(), userDo.getId());
        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int delete(UserDo userDo) {
        String sqlStr = "delete from " + userDo.getTableName() + " where userName=?";
        int result = jdbcTemplate.update(sqlStr, userDo.getUserName());
        return result;
    }

}
