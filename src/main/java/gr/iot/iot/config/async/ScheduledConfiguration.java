package gr.iot.iot.config.async;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by smyrgeorge on 4/18/17.
 */
@Configuration
@EnableScheduling
public class ScheduledConfiguration implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledConfiguration.class);

    @Autowired
    private Environment env;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(name = "scheduledTaskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Scheduled Task Executor");

        int corePoolSize = Integer.parseInt(env.getProperty("spring.async.corePoolSize"));
        DefaultThreadFactory threadFactory = new DefaultThreadFactory("scheduled");
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }
}
