package com.shotacon.wx.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class City {

//	  `CITY_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增列',
//	  `CITY_CODE` varchar(40) NOT NULL COMMENT '市代码',
//	  `CITY_NAME` varchar(40) NOT NULL COMMENT '市名称',
//	  `SHORT_NAME` varchar(20) NOT NULL COMMENT '简称',
//	  `PROVINCE_CODE` varchar(40) DEFAULT NULL COMMENT '省代码',
//	  `LNG` varchar(20) DEFAULT NULL COMMENT '经度',
//	  `LAT` varchar(20) DEFAULT NULL COMMENT '纬度',
//	  `SORT` int(6) DEFAULT NULL COMMENT '排序',
//	  `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
//	  `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
//	  `MEMO` varchar(250) DEFAULT NULL COMMENT '备注',
//	  `DATA_STATE` int(11) DEFAULT NULL COMMENT '状态',
//	  `TENANT_CODE` varchar(32) DEFAULT NULL COMMENT '租户ID',
	private long CITY_ID;
	private String city_code;
	private String city_name;
	private String short_name;
	private String province_code;
	private String lng;
	private String lat;
	private int sort;
	private Date gmt_create;
	private Date gmt_modified;
}
