package com.shotacon.wx.config.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shotacon.wx.mapper.WxStuffMapper;

@Component
public class WxErrorCode {

	private static WxStuffMapper mapper;

	@Autowired
	private WxStuffMapper wxStuffMapper;

	private static Map<String, String> wxErrorCode = new HashMap<String, String>();

	@PostConstruct
	private void init() {
		WxErrorCode.mapper = wxStuffMapper;
		List<Map<String, String>> resultLit = mapper.queryAllErrorCode();
		resultLit.forEach(each -> wxErrorCode.putAll(each));
	}

	public static String get(String key) throws Exception {
		if (wxErrorCode.isEmpty())
			return "";
		return wxErrorCode.get(key);
	}

}
