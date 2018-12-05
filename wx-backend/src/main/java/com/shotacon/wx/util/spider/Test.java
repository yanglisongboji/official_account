package com.shotacon.wx.util.spider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.ObjectUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.io.Files;
import com.shotacon.wx.entity.MessageEntity;

public class Test {

	public static void main(String[] args) throws Exception {

//		String url = "https://sexyteenboyss.tumblr.com";
//		String url = "https://neverspitsonlyswallows.tumblr.com/";
//		Document parse = Jsoup.parse(TumblrSpiderUtil.getHtml(url));
//		
//		Files.write(parse.html(), Paths.get("/data/temp/abcd.txt").toFile(), StandardCharsets.UTF_8);

		MessageEntity a = new MessageEntity();
		a.setContent("123123");
		MessageEntity clone = ObjectUtils.clone(a);
		System.out.println(clone.getContent());
	}
}
