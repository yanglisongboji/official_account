package com.shotacon.web.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shotacon.web.config.RedisUtil;
import com.shotacon.web.service.CommonService;
import com.shotacon.web.utils.movie.BoxOffice;
import com.shotacon.web.utils.movie.Mtime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class MovieJob {

    @Autowired
    RedisUtil redis;

    @Autowired
    MongoTemplate mongoTemplate;

    @Scheduled(initialDelay = 0, fixedDelay = 1000 * 60 * 60)
    public void updateDailyBoxOffice() {
        log.info("update DailyBoxOffice begin");
        BoxOffice dailyBoxOffice = Mtime.dailyBoxOffice();
        redis.set(CommonService.DAILY_BOX_OFFICE, dailyBoxOffice);
        mongoTemplate.save(dailyBoxOffice, "DailyBoxOffice");
        log.info("update DailyBoxOffice end");
    }

}
