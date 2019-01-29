package com.shotacon.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shotacon.web.config.RedisUtil;

@Service
public abstract class CommonService {

    public static final String DAILY_BOX_OFFICE = "DailyBoxOffice";

    @Autowired
    RedisUtil redis;

}
