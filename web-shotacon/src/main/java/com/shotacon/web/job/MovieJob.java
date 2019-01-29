package com.shotacon.web.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shotacon.web.config.RedisUtil;
import com.shotacon.web.service.CommonService;
import com.shotacon.web.utils.movie.Mtime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class MovieJob {

    @Autowired
    RedisUtil redis;

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 60)
    public void updateDailyBoxOffice() {
        log.info("update DailyBoxOffice begin");
        redis.set(CommonService.DAILY_BOX_OFFICE, Mtime.dailyBoxOffice());
        log.info("update DailyBoxOffice end");
    }

}
