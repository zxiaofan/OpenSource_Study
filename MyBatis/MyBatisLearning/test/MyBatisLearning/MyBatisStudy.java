/*
 * 文件名：MyBatisStudy.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MyBatisStudy.java
 * 修改人：yunhai
 * 修改时间：2016年4月20日
 * 修改内容：新增
 */
package MyBatisLearning;

import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import zxiaofan.model.StudentVo;
import zxiaofan.service.IUserService;

/**
 * 最简单最基础的Mybatis用法
 * 
 * @author yunhai
 */
public class MyBatisStudy extends BaseTest {
    @Resource(name = "userService")
    private IUserService gInfoDao;

    @Test
    public void getInfo() {
        // mybatis的配置文件
        String resource = "zxiaofan/config/mybatis-config.xml";
        // 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = MyBatisStudy.class.getClassLoader().getResourceAsStream(resource);
        // 构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        // 使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        // Reader reader = Resources.getResourceAsReader(resource);
        // 构建sqlSession的工厂
        // SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        /**
         * 映射sql的标识字符串， src.config.mapper是mapper.xml文件中mapper标签的namespace属性的值， getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "src.config.mapper.getUser";// 映射sql的标识字符串
        // 执行查询返回一个唯一user对象的sql
        StudentVo user = session.selectOne(statement, 5);
        System.out.println(user.getName());
    }

    /**
     * MyBatis+Spring整合.
     * 
     */
    @Test
    public void testMyBatisSpring() {
        StudentVo vo = gInfoDao.getInfo(5);
        System.out.println(vo.getName());
    }
}
