package com.shotacon.wx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shotacon.wx.entity.District;
import com.shotacon.wx.entity.Street;
import com.shotacon.wx.entity.Town;

@Mapper
public interface TestMapper {

	@Select("select t.*,d.id as did from town t left join district d on d.code = t.country_id ")
	public List<Town> queryStreet();

	@Insert("<script> insert into district_temp (`name` ,parentid ,initial, initials ,pinyin ,suffix ,`code` ,`order` ,full_name) "
			+ " values "
			+ " <foreach item='t' index='index' collection='list' separator=',' > "
			+ " ("
			+ "  #{t.name}, "
			+ "  #{t.parentid}, "
			+ "  #{t.initial},"
			+ "  #{t.initials},"
			+ "  #{t.pinyin},"
			+ "  #{t.suffix},"
			+ "	 #{t.code},"
			+ "  #{t.order},"
			+ "  #{t.full_name}"
			+ " )" 
			+ " </foreach> "
			+ " </script> ")
	public int insertDistrict(@Param("list") List<District> list);

	@Select("select t.*,d.id as area_id from bs_street t left join district d on d.code = t.area_code where t.STREET_ID = 1007 ")
	public Street queryStreetSingle();

	@Select("select t.*, d.id as dId from district_temp t  left join town o on t.`code` = o.town_id left join district d on d.`code` = o.country_id ")
	public List<Map<String, Object>> queryD();

	@Update("update district_temp set parentid = #{did} where id = #{id}")
	public int update(@Param("id") Object id, @Param("did") Object did);

}
