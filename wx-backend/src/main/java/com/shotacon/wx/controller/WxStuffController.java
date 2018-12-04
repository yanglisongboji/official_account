package com.shotacon.wx.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.constant.WxUrl;
import com.shotacon.wx.entity.MessageEntity;
import com.shotacon.wx.util.RestSSLClient;
import com.shotacon.wx.util.SignatureUtil;
import com.shotacon.wx.util.StreamUtil;
import com.shotacon.wx.util.aes.AesException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = { "微信相关接口" })
public class WxStuffController {

	@PostMapping("/signature")
	@ApiOperation(value = "验证消息", notes = "验证消息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String postHandler(HttpServletRequest request, HttpServletResponse response) {
		try {
			MessageEntity acceptMessage = SignatureUtil.acceptMessage(request.getInputStream());
			log.info(acceptMessage.toString());
			return SignatureUtil.sendTextMsg(acceptMessage, "get~");
		} catch (IOException | AesException e) {
			log.error("parse xml to entity error, {}", e.getMessage());
			return "success";
		}
	}

	@GetMapping("/signature")
	@ApiOperation(value = "验证消息", notes = "验证消息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String checkSignature(
			@ApiParam(value = "微信加密签名", name = "signature") @RequestParam(value = "signature", required = true) String signature,
			@ApiParam(value = "时间戳", name = "timestamp") @RequestParam(value = "timestamp", required = true) String timestamp,
			@ApiParam(value = "随机数", name = "nonce") @RequestParam(value = "nonce", required = true) String nonce,
			@ApiParam(value = "随机字符串", name = "echostr") @RequestParam(value = "echostr", required = true) String echostr) {

		if (SignatureUtil.signature(WxUrl.wxConfig.getToken(), timestamp, nonce, signature)) {
			// 验证通过时获取ac
			accessToken();
			return echostr;
		}
		return StringUtils.EMPTY;
	}

	@GetMapping("/accesstoken")
	@ApiOperation(value = "获取Access_token", notes = "获取Access_token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String accessToken() {
		return SignatureUtil.reFreshAccessToken();
	}

	@PostMapping("/menu")
	@ApiOperation(value = "创建菜单", notes = "创建菜单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String createMenu() {
		try {
			ClassPathResource cpr = new ClassPathResource("menu.json");
			String jsonStr = StreamUtil.inputStreamToString(cpr.getInputStream());
			log.info(jsonStr);
			JSONObject json = JSON.parseObject(jsonStr);
			JSONObject body = RestSSLClient.httpsRestTemplate
					.postForEntity(WxUrl.POST_MENU_URL(), json.toJSONString(), JSONObject.class).getBody();
			return body.toJSONString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@PostMapping("/template/send")
	@ApiOperation(value = "发送模板消息", notes = "发送模板消息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String sendTemplate(
			@ApiParam(value = "模板内容", name = "content") @RequestParam(value = "content", required = true) String content) {
		String body = RestSSLClient.httpsRestTemplate
				.postForEntity(WxUrl.POST_SEND_TEMPLATE_URL(), content, String.class).getBody();
		log.info(body);
		return body;
	}

	@GetMapping("/user/list")
	@ApiOperation(value = "获取所有用户", notes = "获取所有用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String listUser() {
		JSONObject body = RestSSLClient.httpsRestTemplate.getForEntity(WxUrl.GET_LIST_USER_URL(), JSONObject.class)
				.getBody();
		return body.toJSONString();
	}

}
