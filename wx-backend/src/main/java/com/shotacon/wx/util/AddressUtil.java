package com.shotacon.wx.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.promeg.pinyinhelper.Pinyin;
import com.shotacon.wx.entity.District;
import com.shotacon.wx.entity.District.DistrictBuilder;
import com.shotacon.wx.entity.Street;
import com.shotacon.wx.entity.Town;

public class AddressUtil {
	private static List<String> list = new ArrayList<>();

	private final static String ZeroOfFive = "00000";

	static {
		list.add("镇");
		list.add("乡");
		list.add("街道");
		list.add("经济开发区");
		list.add("开发区");
		list.add("管委会");
		list.add("委员会");
		list.add("管理处");
		list.add("管理区");
		list.add("园区");
		list.add("办事处");
		list.add("农场");
		list.add("牧场");
		list.add("林场");
		list.add("所");
		list.add("保护区");
		list.add("名胜风景区");
		list.add("风景区");
		list.add("度假区");
		list.add("试验区");
		list.add("局");
		list.add("服务站");
		list.add("场");
		list.add("工业园");
		list.add("工业区");
		list.add("监狱");
		list.add("服务中心");
		list.add("管理中心");
		list.add("有限责任公司");
		list.add("公司");
		list.add("集团");
		list.add("厂");
		list.add("煤矿");
	}

	public static String checkIsLast(String name) {
		for (String last : list) {
			if (name.lastIndexOf(last) > 0) {
				return last;
			}
		}
		return "";
	}

	public static DistrictBuilder convert(String name) {
		// 行政划分
		String type = AddressUtil.checkIsLast(name);
		// 前名
		int index = name.lastIndexOf(type);
		String f_name = name;
		if (index > 0) {
			f_name = name.substring(0, index);
		}
		// 前名拼音
		String pinyin = Pinyin.toPinyin(f_name, "_").toLowerCase();
		String p = StringUtils.replace(pinyin, "_", "");
		String[] split = pinyin.split("_");
		List<String> collect = Arrays.stream(split).map(str -> String.valueOf(str.charAt(0)))
				.collect(Collectors.toList());
		// 词汇首字母拼音
		String join = String.join("", collect);
		return District.builder()
				// 全名
				.full_name(name)
				// 前名
				.name(f_name)
				// 行政划分
				.suffix(type)
				// 拼音
				.pinyin(p)
				// 拼音首字母
				.initial(String.valueOf(pinyin.charAt(0)))
				// 词汇首字母拼音
				.initials(join);

	}

	public static <T> List<T> loadStreetJson(Class<T> c) throws IOException {
		String filePath = "/Users/shotacon/git/china_regions/src/1/town.json";
		Path path = Paths.get(filePath);
		StringBuffer sb = new StringBuffer();
		Files.readAllLines(path).stream().forEach(line -> sb.append(line));
		JSONArray jsonArray = JSON.parseObject(sb.toString()).getJSONArray("RECORDS");
		List<T> parseArray = JSON.parseArray(jsonArray.toJSONString(), c);
		return parseArray;
	}

	public static String fillZero(String str) {
		return str.substring(0, 7) + ZeroOfFive;
	}

	public static void main(String[] args) throws IOException {
//		List<Town> loadStreetJson = loadStreetJson(Town.class);
//		System.out.println(loadStreetJson);
		// 110101001
		// {"id":"460400500498","name":"华南热作学院虚拟社区"}
		// _id=null, id=110101001001, name=多福巷社区居委会, town_id=110101000000)
		String id = "460400500498";
		int index = id.lastIndexOf("0");
		String substring = id.substring(index + 1, id.length());
		System.out.println(substring);
		
		long a = 110105000L;
		long b = 110105400L;
		System.out.println(a - b);

	}
}
