package com.shotacon.web.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobConfig {

    public ThreadPoolTaskExecutor threadPoolTaskExecutor;
    
    @Bean
    @PostConstruct
    public AsyncTaskExecutor taskExecutor() {
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("pool-thread");
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(6);
        threadPoolTaskExecutor.setDaemon(true);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.initialize();
        log.info("ThreadPoolTaskExecutor Initialize Success.");
        return threadPoolTaskExecutor;
    }

    @PreDestroy
    public void destroy() {
        if (threadPoolTaskExecutor != null) {
            threadPoolTaskExecutor.shutdown();
            log.info("ThreadPoolTaskExecutor ShutDown.");
        }
    }
}
