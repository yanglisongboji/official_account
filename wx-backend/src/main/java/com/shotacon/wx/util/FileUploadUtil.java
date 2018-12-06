package com.shotacon.wx.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shotacon.wx.config.constant.WxUrl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUploadUtil {

	public enum Devie {
		device_key, device_id, profile_name;
	}

	private final static String GET_DEVICE_KEY_URL = "https://send-anywhere.com/web/v1/device";

	private final static String UPLOAD_URL = "https://send-anywhere.com/web/v1/key";

	private static JSONObject deviceKey = null;

	public static String registDevice() {

		StringBuffer urlBuffer = new StringBuffer(GET_DEVICE_KEY_URL);

//		urlBuffer.append("?api_key=e5e7a67f51279e43d9dddc5986ed2392fb1b4bba");
		urlBuffer.append("?api_key").append(WxUrl.wxConfig.getApiKey());
		urlBuffer.append("&profile_name").append("shotaconXD");

		log.info("registDevice url: {}", urlBuffer.toString());
		ResponseEntity<JSONObject> response = RestSSLClient.httpsRestTemplate.getForEntity(urlBuffer.toString(),
				JSONObject.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			deviceKey = response.getBody();

			List<String> cookies = new ArrayList<>();
			cookies.add("device_key=" + deviceKey.getString(Devie.device_key.name()));
			RestSSLClient.headers.put(HttpHeaders.COOKIE, cookies);

			return deviceKey.getString(Devie.device_key.name());
		}
		log.error("registDevice fail with {}", response.getStatusCode());
		return StringUtils.EMPTY;
	}

	public static String uploadFile(List<File> files) {

		StringBuffer urlBuffer = new StringBuffer(UPLOAD_URL);

		urlBuffer.append("?api_key").append(WxUrl.wxConfig.getApiKey());
//		urlBuffer.append("?api_key=e5e7a67f51279e43d9dddc5986ed2392fb1b4bba");
		JSONObject map = new JSONObject();
		JSONArray array = new JSONArray();

		files.forEach(file -> {
			JSONObject fileMap = new JSONObject();
			fileMap.put("name", file);
			fileMap.put("size", file.length());
			array.add(fileMap);
		});
		map.put("file", array);

		if (RestSSLClient.headers.get("device_key") == null) {
			registDevice();
		}

		ResponseEntity<JSONObject> response = RestSSLClient.httpsRestTemplateForMultipart.exchange(urlBuffer.toString(),
				HttpMethod.GET, RestSSLClient.entityFromMap(map), JSONObject.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			log.info("upload success, {}", response.getBody().toJSONString());
			return response.getBody().getString("link");
		}
		return StringUtils.EMPTY;
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("/data/temp/ehuro/");
		List<File> files = Files.list(path).map(p -> p.toFile()).collect(Collectors.toList());

		String uploadFile = uploadFile(files);

		System.out.println(uploadFile);
	}

}
