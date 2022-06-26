package com.wei.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Administrator
 */
@Configuration
public class ScheduledConfig {

    @Primary
    @Bean
    public TaskScheduler scheduledTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("Scheduled-");
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setRemoveOnCancelPolicy(true);
        return scheduler;
    }
}