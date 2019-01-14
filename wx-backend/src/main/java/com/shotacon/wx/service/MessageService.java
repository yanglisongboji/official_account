package com.shotacon.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shotacon.wx.entity.MessageEntity;
import com.shotacon.wx.mapper.MessageMongoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	MessageMongoRepository<MessageEntity, String> messageMongoRepository;

	public void save(MessageEntity messageEntity) {
		try {
			messageMongoRepository.save(messageEntity);
		} catch (Exception e) {
			log.error("error: ", e);
		}
	}

}
