package com.shotacon.wx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WxStuffMapper {

	@Select("select code, msg from wx_error_code")
	public List<Map<String, String>> queryAllErrorCode();
}
