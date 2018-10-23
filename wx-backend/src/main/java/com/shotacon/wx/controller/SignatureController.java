package com.shotacon.wx.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shotacon.wx.config.constant.WxConfig;
import com.shotacon.wx.util.SignatureUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = { "微信相关接口" })
public class SignatureController {

	@Autowired
	private WxConfig wxConfig;

	@GetMapping("/signature")
	@ApiOperation(value = "验证消息", notes = "验证消息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String checkSignature(
			@ApiParam(value = "微信加密签名", name = "signature") @RequestParam(value = "signature", required = true) String signature,
			@ApiParam(value = "时间戳", name = "timestamp") @RequestParam(value = "timestamp", required = true) String timestamp,
			@ApiParam(value = "随机数", name = "nonce") @RequestParam(value = "nonce", required = true) String nonce,
			@ApiParam(value = "随机字符串", name = "echostr") @RequestParam(value = "echostr", required = true) String echostr) {

		if (SignatureUtil.signature(wxConfig.getToken(), timestamp, nonce, signature)) {
			return echostr;
		}
		return StringUtils.EMPTY;
	}

}
