package com.wei.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.*;

/**
 * @author Administrator
 */
@Configuration
public class ThreadPoolConfig {
    /**
     * 核心线程池大小
     */
    @Value("${spring.service.core-pool.core-pool-size}")
    private Integer corePoolSize;

    /**
     * 最大核心线程池大小
     */
    @Value("${spring.service.core-pool.maximum-pool-size}")
    private Integer maximumPoolSize;

    /**
     * 空闲线程存活时间秒s
     */
    @Value("${spring.service.core-pool.keep-alive-time}")
    private Integer keepAliveTime;

    /**
     * 阻塞队列大小
     */
    @Value("${spring.service.core-pool.work-queue-size}")
    private Integer workQueueSize;

    @Bean
    @Primary
    public ExecutorService getThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize
                , maximumPoolSize
                , keepAliveTime
                , TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(workQueueSize)
                , Executors.defaultThreadFactory()
        );
        return executorService;
    }
}
