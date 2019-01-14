package com.shotacon.wx.mapper;

import java.io.Serializable;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface MessageMongoRepository<T, ID extends Serializable> extends Repository<T, ID> {

	<S extends T> S save(S entity);
}
