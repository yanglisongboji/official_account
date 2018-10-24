package com.shotacon.wx.config.constant;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WxUrl {

	@Autowired
	private WxConfig wxConfig_Auto;

	public static WxConfig wxConfig;

	@PostConstruct
	public void init() {
		WxUrl.wxConfig = wxConfig_Auto;
	}

	/**
	 * ac map
	 */
	public static Map<String, String> access_token_map = new HashMap<String, String>();

	private final static String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
	private final static String POST_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	private final static String POST_SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	private final static String GET_LIST_USER = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

	/**
	 * 获取所有用户
	 */
	public static String GET_LIST_USER_URL() {
		return urlConnAccessToken(GET_LIST_USER);
	}

	/**
	 * 获取ac
	 */
	public static String GET_ACCESS_TOKEN_URL() {
		return GET_ACCESS_TOKEN + "appid=" + wxConfig.getAppid() + "&secret=" + wxConfig.getAppsecret();
	}

	/**
	 * 创建menu
	 */
	public static String POST_MENU_URL() {
		return urlConnAccessToken(POST_MENU);
	}

	/**
	 * 发送模板消息
	 * 
	 * @return
	 */
	public static String POST_SEND_TEMPLATE_URL() {
		return urlConnAccessToken(POST_SEND_TEMPLATE);
	}

	private static String urlConnAccessToken(String url) {
		return url + access_token_map.get("access_token");
	}
}
