package com.shotacon.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shotacon.web.model.ResMsg;
import com.shotacon.web.service.MovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = { "Movie" }, value = "Movie")
@RequestMapping("/movie")
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @ApiOperation(value = "获取当日票房排行", notes = "获取当日票房排行", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/dailyBoxOffice", method = RequestMethod.GET)
    public ResMsg queryDailyBoxOffice() {
        log.info("获取当日票房排行");
        return ResMsg.succWithData(movieService.queryDailyBoxOffice());
    }
}
