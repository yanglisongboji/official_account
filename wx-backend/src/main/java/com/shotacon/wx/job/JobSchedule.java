package com.shotacon.wx.job;

import com.shotacon.wx.config.JobManager;
import com.shotacon.wx.util.SignatureUtil;

public class JobSchedule {

	static {
		JobManager.addJob("RefreshAccessToken", "wx", "PostConstruct", "startWithService", RefreshAccessTokenJob.class,
				"0 0/55 0/1 * * ?");
		SignatureUtil.reFreshAccessToken();
	}
}
