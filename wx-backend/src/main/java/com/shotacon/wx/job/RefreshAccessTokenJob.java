package com.shotacon.wx.job;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.JobManager;
import com.shotacon.wx.config.constant.WxUrl;
import com.shotacon.wx.util.RestSSLClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RefreshAccessTokenJob implements Job {

	@PostConstruct
	public void init() {
		JobManager.addJob("RefreshAccessToken", "wx", "PostConstruct", "startWithService", RefreshAccessTokenJob.class,
				"0 0/55 0/1 * * ?");
		reFreshAccessToken();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		reFreshAccessToken();
	}

	private void reFreshAccessToken() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credential");
		map.put("appid", WxUrl.wxConfig.getAppid());
		map.put("secret", WxUrl.wxConfig.getAppsecret());
		JSONObject body = RestSSLClient.httpsRestTemplate
				.getForEntity(WxUrl.GET_ACCESS_TOKEN_URL(), JSONObject.class, map).getBody();
		if (!body.containsKey("access_token")) {
			log.error("Get Access Token Error, message: {}", body.toJSONString());
			return;
		}
		WxUrl.access_token_map.put("access_token", body.getString("access_token"));
		WxUrl.access_token_map.put("expires_in", body.getString("expires_in"));
	}

}
