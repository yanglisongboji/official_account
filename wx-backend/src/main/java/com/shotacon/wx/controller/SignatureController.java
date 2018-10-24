package com.shotacon.wx.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.constant.WxUrl;
import com.shotacon.wx.util.RestSSLClient;
import com.shotacon.wx.util.SignatureUtil;
import com.shotacon.wx.util.StreamUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = { "微信相关接口" })
public class SignatureController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

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
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credential");
		map.put("appid", WxUrl.wxConfig.getAppid());
		map.put("secret", WxUrl.wxConfig.getAppsecret());
		JSONObject body = RestSSLClient.httpsRestTemplate
				.getForEntity(WxUrl.GET_ACCESS_TOKEN_URL(), JSONObject.class, map).getBody();
		if (body.containsKey("access_token")) {
			WxUrl.access_token_map.put("access_token", body.getString("access_token"));
			WxUrl.access_token_map.put("expires_in", body.getString("expires_in"));
			return body.toJSONString();
		}
		log.error("Get Access Token Error, message: {}", body.toJSONString());
		return body.toJSONString();
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

}
