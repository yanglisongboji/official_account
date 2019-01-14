package com.shotacon.wx.service;

import java.io.IOException;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.shotacon.wx.entity.MessageEntity;
import com.shotacon.wx.util.ByteUtil;
import com.shotacon.wx.util.SignatureUtil;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	private static XStream xstream = new XStream();

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(MessageEntity messageEntity) {
		try {
			mongoTemplate.save(messageEntity);
		} catch (Exception e) {
			log.error("error: ", e);
		}
	}

	public String acceptMessage(ServletInputStream in) throws IOException {
		String xml = ByteUtil.inputStreamToString(in);
		log.info(xml);
		xstream.processAnnotations(MessageEntity.class);
		xstream.alias("xml", MessageEntity.class);
		MessageEntity message = (MessageEntity) xstream.fromXML(xml);
		MessageEntity reMessage = ObjectUtils.clone(message);
		log.info(message.toString());
		mongoTemplate.save(message);
//		if (StringUtils.isNotEmpty(message.getContent()) && message.getContent().contains("http")) {
//			String content = message.getContent();
//			String[] split = content.split(">>>");
//			if (split.length <= 1) {
//				reMessage.setContent("提交失败, 请注意格式: 月数>>>网址 , 例如: 12>>>http://xxx.tumblr.com");
//				return SignatureUtil.sendTextMsg(reMessage);
//			}
//			executorService.execute(new Runnable() {
//				@Override
//				public void run() {
//					String link = TumblrSpiderUtil.doSpider(split[1].trim(), Integer.valueOf(split[0].trim()));
//					JSONObject param = new JSONObject();
//
//					JSONObject value = new JSONObject();
//					value.put("value", link);
//					value.put("color", "#173177");
//					param.put("link", value);
//
//					value = new JSONObject();
//					value.put("value", LocalDateTime.now().toString());
//					value.put("color", "#173177");
//					param.put("time", value);
//					sendTemplate(param, message);
//				}
//			});
//			reMessage.setContent("提交成功, 等待推送结果.");
//		}
		return SignatureUtil.sendTextMsg(reMessage);
	}

}
