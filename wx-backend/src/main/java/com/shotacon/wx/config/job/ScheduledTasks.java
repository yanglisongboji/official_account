package com.shotacon.wx.config.job;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.shotacon.wx.util.SignatureUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class ScheduledTasks {

	public ThreadPoolTaskExecutor threadPoolTaskExecutor;

	// 每1小时55分钟执行一次
//	@Scheduled(cron = "0 */55 0/1  * * * ")
	@Scheduled(initialDelay = 1000, fixedRate = (60 + 55) * 1000)
	public void reFreshAccessToken() {
		log.info("Start Refresh Access Token.");
		threadPoolTaskExecutor.execute(() -> SignatureUtil.reFreshAccessToken(), 10000);
	}

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
