package com.shotacon.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class District {

//	 `id` smallint(5) NOT NULL,
//	  `name` varchar(90) DEFAULT NULL COMMENT '省/市/区名',
//	  `parentid` smallint(5) DEFAULT NULL COMMENT '上级id',
//	  `initial` char(9) DEFAULT NULL COMMENT '首字母',
//	  `initials` char(90) DEFAULT NULL COMMENT '全首字母',
//	  `pinyin` varchar(270) DEFAULT NULL COMMENT '拼音',
//	  `suffix` varchar(45) DEFAULT NULL COMMENT '行政单位(省市区)',
//	  `code` char(63) DEFAULT NULL COMMENT '行政编码',
//	  `order` tinyint(2) DEFAULT NULL COMMENT '排序',
//	  `full_name` varchar(255) DEFAULT NULL,

	private Long id;
	private String name;
	private Long parentid;
	private String initial;
	private String initials;
	private String pinyin;
	private String suffix;
	private String code;
	private Integer order;
	private String full_name;
}
