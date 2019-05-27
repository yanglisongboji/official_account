package com.shotacon.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class Town {

	private Integer _id;
	private String name;
	private String town_id;
	private String country_id;
	private Integer did;

}
