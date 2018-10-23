package com.shotacon.wx.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: RestSSLClient
 * @Description: TODO(基于Spring RestTemplete实现的SSL客户端)
 * @author shota
 * @date 2018年8月29日
 *
 */
public class RestSSLClient {

	/**
	 * HTTPs 请求
	 */
	public static RestTemplate httpsRestTemplate = new RestTemplate(new HttpsClientRequestFactory());
	/**
	 * HTTPs Multipart 请求
	 */
	public static RestTemplate httpsRestTemplateForMultipart = new RestTemplate(new HttpsClientRequestFactory());
	/**
	 * HTTP 请求
	 */
	public static RestTemplate httpRestTemplate = new RestTemplate();

	/**
	 * HTTPs 请求头
	 */
	public static HttpHeaders headers;
	/**
	 * HTTPs Multipart 请求头
	 */
	public static HttpHeaders headersForMultipart;

	static {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		
		headersForMultipart = new HttpHeaders();
		headersForMultipart.setContentType(MediaType.MULTIPART_FORM_DATA);
	}

	/**
	 * 由{@link Map}创建的{@link HttpEntity}对象
	 * 
	 * @param map
	 * @return {@link HttpEntity}
	 */
	public static HttpEntity<String> entityFromMap(@SuppressWarnings("rawtypes") Map map) {

		return new HttpEntity<>(JSONObject.toJSONString(map), headers);
	}

}
