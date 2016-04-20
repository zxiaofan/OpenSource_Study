package MyBatisLearning;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * @author yunhai
 */
@RunWith(SpringJUnit4ClassRunner.class)
/*
 * @TransactionConfiguration(transactionManager = "transactionManager")
 * 
 * @Transactional
 */
@ContextConfiguration("/MyBatisLearning/config/app-context-mybatis.xml")
public class BaseTest {

    /**
     * 日志记录.
     */
    protected Logger logger = Logger.getLogger(BaseTest.class);

    static {
        try {
            Log4jConfigurer.initLogging("WebContent/WEB-INF/config/log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }

}
