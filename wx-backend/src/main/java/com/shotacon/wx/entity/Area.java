package com.shotacon.wx.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Area {

//	  `AREA_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增列',
//	  `AREA_CODE` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区代码',
//	  `CITY_CODE` varchar(40) DEFAULT NULL COMMENT '父级市代码',
//	  `AREA_NAME` varchar(40) NOT NULL COMMENT '市名称',
//	  `SHORT_NAME` varchar(20) NOT NULL COMMENT '简称',
//	  `LNG` varchar(20) DEFAULT NULL COMMENT '经度',
//	  `LAT` varchar(20) DEFAULT NULL COMMENT '纬度',
//	  `SORT` int(6) DEFAULT NULL COMMENT '排序',
//	  `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
//	  `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
	private long area_id;
	private String area_code;
	private String city_code;
	private String area_name;
	private String short_name;
	private String lng;
	private String lat;
	private int sort;
	private Date gmt_create;
	private Date gmt_modified;

}
