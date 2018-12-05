package com.shotacon.wx.util.spider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.env.SystemEnvironmentPropertySource;

public class Test {

	public static void main(String[] args) throws Exception {

//		String url = "https://sexyteenboyss.tumblr.com";
//		String url = "https://neverspitsonlyswallows.tumblr.com/";
//		Document parse = Jsoup.parse(TumblrSpiderUtil.getHtml(url));
//
//		Files.write(Paths.get("/data/temp/abcd.txt"), parse.html().getBytes());
		
		Set<String> a = new HashSet<>();
//		a.add("https://sexyteenboyss.tumblr.com/post/179890701450/guycloud-anyone-know-these-two");
		a.add("https://sexyteenboyss.tumblr.com/post/178144021680");
//		a.add("https://sexyteenboyss.tumblr.com/post/180047517005/follow-my-blog-assboyslut-for-more-hot-post-like");
//		Map<String, Set<String>> handlePostList = TumblrSpiderUtil.handlePostList(a);
//		Elements elementsByClass = Jsoup.parse(TumblrSpiderUtil.getHtml("https://lookingforveins2.tumblr.com/post/170682800171")).getElementsByClass("photo-wrapper-inner");
		Elements elementsByClass = Jsoup.parse(TumblrSpiderUtil.getHtml("https://lookingforveins2.tumblr.com/post/170682800171")).getElementsByTag("img");
		
		String attr = elementsByClass.get(0).attr("src");
		
		System.out.println(attr);
		
		
//		for (Element element : elementsByClass) {
//			String elementsByTag = element.getElementsByTag("img").get(0).attr("src");
//			System.out.println(elementsByTag);
//			
//			String element2 = element.getElementsByAttribute("src").get(0).attr("src");
//			
//			System.out.println(element2);
//			
//			break;
//		}
		
//		String url = "https://lookingforveins2.tumblr.com/archive/2018/08";
//		Set<String> allPostByUrl = TumblrSpiderUtil.getAllPostByUrl(url, "2018/08");
//		
//		
//		
//		System.out.println(allPostByUrl);
		
	}
}
