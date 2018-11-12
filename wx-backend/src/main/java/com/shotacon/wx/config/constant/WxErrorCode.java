package com.shotacon.wx.config.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shotacon.wx.mapper.WxStuffMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WxErrorCode {

	private static WxStuffMapper mapper;

	@Autowired
	private WxStuffMapper wxStuffMapper;

	/**
	 * key: code, msg
	 */
	private static Map<String, String> wxErrorCode = new HashMap<String, String>();

	@PostConstruct
	private void init() {
		WxErrorCode.mapper = wxStuffMapper;
		List<Map<String, String>> resultList = mapper.queryAllErrorCode();
		resultList.forEach(each -> wxErrorCode.putAll(each));
		log.info("Init wx_error_code done, total {} of data", resultList.size());
	}

	public static String get(String key) throws Exception {
		if (wxErrorCode.isEmpty())
			return "";
		return wxErrorCode.get(key);
	}

}
