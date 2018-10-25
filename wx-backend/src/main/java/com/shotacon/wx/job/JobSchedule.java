package com.shotacon.wx.job;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.shotacon.wx.config.JobManager;
import com.shotacon.wx.util.SignatureUtil;

@Component
public class JobSchedule {

	@PostConstruct
	public void init() {
		JobManager.addJob("RefreshAccessToken", "wx", "PostConstruct", "startWithService", RefreshAccessTokenJob.class,
				"0 0/55 0/1 * * ?");
		SignatureUtil.reFreshAccessToken();
	}
}
