package openSymphony.quartz.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author yunhai
 *
 *         用监听器启动任务，代替main启动
 */
public class StartUpListener implements ServletContextListener {

    /**
     * 添加字段注释.
     */
    private static final Log LOG = LogFactory.getLog(StartUpListener.class);

    /**
     * 服务启动的业务层.
     */
    private IStartBusiness StartBusiness;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("StartUpListener initializing context...");
        }
        try {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
            StartBusiness = webApplicationContext.getBean(IStartBusiness.class);
            StartBusiness.startWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
