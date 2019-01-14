package com.shotacon.wx.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;

import com.shotacon.wx.entity.MessageEntity;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamUtil {

	public static String inputStreamToString(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}

	public static MessageEntity handleMessage(ServletInputStream in) throws UnsupportedEncodingException, IOException {
		XStream xstream = new XStream();
		xstream.processAnnotations(MessageEntity.class);
		xstream.alias("xml", MessageEntity.class);
		// 将流转换为字符串
		StringBuilder xmlMsg = new StringBuilder();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			xmlMsg.append(new String(b, 0, n, "UTF-8"));
		}
		log.info(xmlMsg.toString());
		MessageEntity message = (MessageEntity) xstream.fromXML(xmlMsg.toString());
		return message;
	}
}
