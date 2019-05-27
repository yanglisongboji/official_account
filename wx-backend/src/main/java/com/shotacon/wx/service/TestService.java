package com.shotacon.wx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shotacon.wx.aspect.TestMsg;
import com.shotacon.wx.entity.District;
import com.shotacon.wx.entity.District.DistrictBuilder;
import com.shotacon.wx.entity.Street;
import com.shotacon.wx.entity.Town;
import com.shotacon.wx.mapper.TestMapper;
import com.shotacon.wx.util.AddressUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {
	
	String a;
	
	@Autowired
	private TestMapper testMapper;

	@TestMsg
	public void save(String msg) {
		try {
			System.out.println("111");
		} catch (Exception e) {
			log.error("error: ", e);
		}
	}

	public void update() {
		List<Map<String, Object>> dList = testMapper.queryD();
		dList.forEach(d -> {
			log.info(JSON.toJSONString(d));
			testMapper.update(Long.valueOf(String.valueOf(d.get("id"))), Long.valueOf(String.valueOf(d.get("did"))));
		});

	}

	public void queryStreet() throws IOException {
//		List<Town> townList = AddressUtil.loadStreetJson(Town.class);
		List<Town> townList = testMapper.queryStreet();
		List<District> districtList = new ArrayList<>();
		log.info("townList size: {}", townList.size());

		townList.forEach(street -> {
			// 街道名称
			String streetName = street.getName();
			log.info("街道名称: {}", streetName);
			DistrictBuilder districtBuilder = AddressUtil.convert(streetName);
			District district = districtBuilder.code(street.getTown_id())
					.order(sub(street.getCountry_id(), street.getTown_id()))
					.parentid(null == street.getDid() ? 0L : street.getDid()).build();
			districtList.add(district);
			log.info(district.toString());
		});
		log.info("districtList size: {}", districtList.size());
		testMapper.insertDistrict(districtList);
		log.info("done");
	}

	private int sub(String a, String b) {
		Long al = Long.valueOf(a);
		Long bl = Long.valueOf(b);
		Long cl = bl - al;
		return cl.intValue() / 1000;
	}

	public static void main(String[] args) {
		String s = "镇罗营镇";
		System.out.println(AddressUtil.convert(s).build());
	}

	public Street queryStreetSingle() {
		Street street = testMapper.queryStreetSingle();
//		// 街道名称
//		String streetName = street.getStreet_name();
//		DistrictBuilder districtBuilder = AddressUtil.convert(streetName);
//		District district = districtBuilder.code(street.getStreet_code()).order(street.getSort())
//				.parentid(null == street.getArea_id() ? 0L : Long.valueOf(street.getArea_id())).build();
//		log.info(district.toString());
//		List<District> districtList = new ArrayList<>();
//		districtList.add(district);
//		testMapper.insertDistrict(districtList);
		return street;
	}

}
