package com.shotacon.wx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class Street {

	private Long _id;
	private String id;
	private String name;
	private String town_id;

}
