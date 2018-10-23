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

	public static Map<String, String> access_token_map = new HashMap<String, String>();

	private final static String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";

	public static String GET_ACCESS_TOKEN_URL() {
		return GET_ACCESS_TOKEN + "appid=" + wxConfig.getAppid() + "&secret=" + wxConfig.getAppsecret();
	}

	private final static String POST_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

	public static String POST_MENU_URL() {
		return POST_MENU + access_token_map.get("access_token");
	}
}
