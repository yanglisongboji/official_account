package com.shotacon.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.shotacon.wx.entity.MessageEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(MessageEntity messageEntity) {
		try {
			mongoTemplate.save(messageEntity);
		} catch (Exception e) {
			log.error("error: ", e);
		}
	}

}
