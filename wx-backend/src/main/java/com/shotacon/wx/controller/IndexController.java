package com.shotacon.wx.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "Index" }, value = "Index")
@RestController
public class IndexController {

	@ApiOperation(value = "index", notes = "index", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "What's up, Yo.";
	}
}
