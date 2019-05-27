package com.shotacon.wx.controller;

import java.io.IOException;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shotacon.wx.entity.Street;
import com.shotacon.wx.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/test")
@Api(tags = { "测试" }, value = "测试")
public class TestController {

	@Autowired
	TestService testService;

	@ApiOperation(value = "测试index", notes = "测试index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {

			@Override
			public void run() {
				try {
					testService.queryStreet();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		return "index";
	}

	@ApiOperation(value = "测试index", notes = "测试index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public Street test() {
		return testService.queryStreetSingle();
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		testService.update();
		return "";
	}

//    @TestInfo
	@ApiOperation(value = "测试hello", notes = "测试hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public Object hello() {
		testService.save("123");
		return "Hello World";
	}
}
