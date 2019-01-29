package com.shotacon.web.service;

import org.springframework.stereotype.Service;

import com.shotacon.web.utils.movie.BoxOffice;

@Service
public class MovieServiceImpl extends CommonService implements MovieService {

    @Override
    public BoxOffice queryDailyBoxOffice() {
        return (BoxOffice) redis.get(DAILY_BOX_OFFICE);
    }

}
