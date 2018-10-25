package com.shotacon.wx.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.shotacon.wx.util.SignatureUtil;

public class RefreshAccessTokenJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SignatureUtil.reFreshAccessToken();
	}

}
