package com.shotacon.wx.util.spider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.io.Files;

public class Test {

	public static void main(String[] args) throws Exception {
		
//		String url = "https://sexyteenboyss.tumblr.com";
//		String url = "https://neverspitsonlyswallows.tumblr.com/";
//		Document parse = Jsoup.parse(TumblrSpiderUtil.getHtml(url));
//		
//		Files.write(parse.html(), Paths.get("/data/temp/abcd.txt").toFile(), StandardCharsets.UTF_8);
		
		
		Path path = Paths.get("/data/temp/abcd.txt");
		String string = path.toAbsolutePath().toString();
		System.out.println(string);

	}
}
