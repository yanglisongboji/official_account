package com.shotacon.wx.entity;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Province {

//	  `PROVINCE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增列',
//	  `PROVINCE_CODE` varchar(40) NOT NULL COMMENT '省份代码',
//	  `PROVINCE_NAME` varchar(50) NOT NULL COMMENT '省份名称',
//	  `SHORT_NAME` varchar(20) NOT NULL COMMENT '简称',
//	  `LNG` varchar(20) DEFAULT NULL COMMENT '经度',
//	  `LAT` varchar(20) DEFAULT NULL COMMENT '纬度',
//	  `SORT` int(6) DEFAULT NULL COMMENT '排序',
//	  `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
//	  `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',

	private long province_id;
	private String province_code;
	private String province_name;
	private String short_name;
	private String lng;
	private String lat;
	private int sort;
	private Date gmt_create;
	private Date gmt_modified;
}
