/*
 * 文件名：GetInfoDao.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetInfoDao.java
 * 修改人：yunhai
 * 修改时间：2016年4月20日
 * 修改内容：新增
 */
package dao.impl;

import org.springframework.stereotype.Component;

import model.StudentVo;

/**
 * 
 * @author yunhai
 */
@Component("getInfoDao")
public class GetInfoDao extends BaseDaoImpl {
    public StudentVo getInfo(int i) {
        return sqlSessionWrite.selectOne("getUser", i);
    }
}
