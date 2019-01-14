package com.shotacon.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.constant.WxUrl;
import com.shotacon.wx.entity.MessageEntity;
import com.shotacon.wx.entity.MessageEntity.MessageType;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信处理类
 * 
 * @author shotacon
 *
 */
@Slf4j
public class SignatureUtil {

	/**
	 * 验证url
	 * 
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @return
	 */
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

	/**
	 * 刷新ac
	 * 
	 * @return
	 */
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

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MILLISECOND, body.getIntValue("expires_in"));

		WxUrl.access_token_map.put("access_token", body.getString("access_token"));
		WxUrl.access_token_map.put("expires_in", now.getTimeInMillis());

		log.info("ReFresh AccessToken Success. token -> {}", body.getString("access_token"));
		return body.toJSONString();
	}

	/**
	 * 发送模板消息
	 * 
	 * @param param
	 * @param message
	 */
	public static void sendTemplate(JSONObject param, MessageEntity message) {
		JSONObject send = new JSONObject();
		send.put("touser", message.getFromUserName());
		send.put("template_id", WxUrl.wxConfig.getTemplateIdForNotice());
		send.put("url", param.getJSONObject("link").getString("value"));
		send.put("data", param);

		JSONObject body = RestSSLClient.httpsRestTemplate
				.postForEntity(WxUrl.POST_SEND_TEMPLATE_URL(), send.toJSONString(), JSONObject.class).getBody();
		log.info("Send template message to {} success, {}", message.getFromUserName(), body.toJSONString());

	}

	/**
	 * 回复文本消息
	 * 
	 * @param requestMap
	 * @param content
	 * @return
	 */
	public static String sendTextMsg(MessageEntity messageEntity) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(messageEntity.getFromUserName()).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(messageEntity.getToUserName()).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(System.currentTimeMillis()).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[").append(MessageType.TEXT).append("]]></MsgType>");
		sb.append("<Content><![CDATA[").append("感谢反馈").append("]]></Content>");
//		sb.append("<Content><![CDATA[").append(messageEntity.getContent()).append("]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		System.out.println(sb.toString());
	}
}
