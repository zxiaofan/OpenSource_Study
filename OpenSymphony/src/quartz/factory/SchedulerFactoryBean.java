package quartz.factory;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Scheduler注入，单例
 */
public class SchedulerFactoryBean implements FactoryBean<Scheduler> {
    /**
     * Scheduler.
     */
    private Scheduler scheduler;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Scheduler getObject() throws Exception {
        if (scheduler == null) {
            scheduler = new StdSchedulerFactory().getScheduler();
        }
        return scheduler;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Class<?> getObjectType() {
        return Scheduler.class;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

}
