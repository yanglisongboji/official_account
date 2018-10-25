package com.shotacon.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.constant.WxUrl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignatureUtil {

	public static boolean signature(String token, String timestamp, String nonce, String signature) {
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);
		String tmpStr = null;
		try {
			tmpStr = ByteUtil.byteToStr(MessageDigest.getInstance("SHA-1").digest(StringUtils.join(arr).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	public static String reFreshAccessToken() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credential");
		map.put("appid", WxUrl.wxConfig.getAppid());
		map.put("secret", WxUrl.wxConfig.getAppsecret());
		JSONObject body = RestSSLClient.httpsRestTemplate
				.getForEntity(WxUrl.GET_ACCESS_TOKEN_URL(), JSONObject.class, map).getBody();
		if (!body.containsKey("access_token")) {
			log.error("Get Access Token Error, message: {}", body.toJSONString());
			return body.toJSONString();
		}
		WxUrl.access_token_map.put("access_token", body.getString("access_token"));
		WxUrl.access_token_map.put("expires_in", body.getString("expires_in"));
		log.info("ReFresh AccessToken Success. token -> {}", body.getString("access_token"));
		return body.toJSONString();
	}
}
