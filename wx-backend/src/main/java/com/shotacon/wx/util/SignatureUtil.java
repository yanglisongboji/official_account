package com.shotacon.wx.util;

import java.io.IOException;
import java.io.InputStream;
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
import com.shotacon.wx.util.aes.AesException;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignatureUtil {

	private static XStream xstream = new XStream();

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

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MILLISECOND, body.getIntValue("expires_in"));

		WxUrl.access_token_map.put("access_token", body.getString("access_token"));
		WxUrl.access_token_map.put("expires_in", now.getTimeInMillis());

		log.info("ReFresh AccessToken Success. token -> {}", body.getString("access_token"));
		return body.toJSONString();
	}

	public static MessageEntity acceptMessage(InputStream in) throws IOException, AesException {
		String xml = ByteUtil.inputStreamToString(in);
		log.info(xml);

//		WXBizMsgCrypt pc = new WXBizMsgCrypt(WxUrl.wxConfig.getToken(), WxUrl.wxConfig.getEncodingAESKey(),
//				WxUrl.wxConfig.getAppid());
//		pc.decryptMsg(msgSignature, timeStamp, nonce, postData)

		xstream.processAnnotations(MessageEntity.class);
		xstream.alias("xml", MessageEntity.class);
		MessageEntity message = (MessageEntity) xstream.fromXML(xml);
		return message;
	}
}
