package com.shotacon.wx.util.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) throws Exception {
		
		List<String> a = new ArrayList<>();
		
		a.add("啊啊啊啊");
		a.add("313123");
		a.add("埃索达");
		a.add("三大三大四的");
		
		List<String> collect = a.stream().filter(str -> !str.contains("313123")).collect(Collectors.toList());
		
		System.out.println(collect);
		
	}
}
