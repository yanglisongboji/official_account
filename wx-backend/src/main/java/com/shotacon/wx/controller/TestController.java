package com.shotacon.wx.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/test")
@Api(tags = { "测试" }, value = "测试")
public class TestController {

	@ApiOperation(value = "测试index", notes = "测试index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}
