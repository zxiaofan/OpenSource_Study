package zxiaofan.dao.impl;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

/**
 * 获取数据库连接.
 * 
 * @author qinfei
 *
 */
@Component("baseDao")
public class BaseDaoImpl {
    /**
     * 写库.
     */
    @Resource(name = "sqlSession")
    protected SqlSessionTemplate sqlSessionWrite;

    /**
     * 读库.
     */
    @Resource(name = "sqlSession")
    protected SqlSessionTemplate sqlSessionRead;

    /**
     * TODO 添加方法注释.
     * 
     * @return SqlSessionTemplate
     */
    public SqlSessionTemplate getSqlSessionWrite() {
        return sqlSessionWrite;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param sqlSessionWrite
     *            sqlSessionWrite
     */
    public void setSqlSessionWrite(SqlSessionTemplate sqlSessionWrite) {
        this.sqlSessionWrite = sqlSessionWrite;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return SqlSessionTemplate
     */
    public SqlSessionTemplate getSqlSessionRead() {
        return sqlSessionRead;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param sqlSessionRead
     *            sqlSessionRead
     */
    public void setSqlSessionRead(SqlSessionTemplate sqlSessionRead) {
        this.sqlSessionRead = sqlSessionRead;
    }

}
