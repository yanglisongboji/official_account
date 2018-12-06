package com.shotacon.wx.util.spider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws Exception {

		String url = "https://neverspitsonlyswallows.tumblr.com/archive/";
		String homeUrl = "https://neverspitsonlyswallows.tumblr.com/";
		String month = "2017/05";
		Set<String> urlPostList = new HashSet<String>();

		List<Elements> eleList = new ArrayList<>();

		String yearMonth = month.replace("/", "");
		String post = TumblrUtil.getUrl(homeUrl) + "post/";
		try {
			String html = TumblrSpiderUtil.getHtml(url);
			Document doc = Jsoup.parse(html);

			eleList.add(doc.getElementsByTag("section"));
			for (int i = 0; i < 4; i++) {
				String html1 = TumblrSpiderUtil.getHtml(
						url + "?before_time=" + doc.getElementById("next_page_link").attr("href").split("=")[1]);
				eleList.add(Jsoup.parse(html1).getElementsByTag("section"));
			}

			for (Elements elements : eleList) {
				for (int i = 0; i < elements.size(); i++) {
					// section
					Element section = elements.get(i);
					if (section.id().contains("posts_") && section.id().endsWith(yearMonth)) {

						Elements elementsByTag = section.getElementsByTag("a");
						elementsByTag.forEach(elementByTag -> {
							String aHref = elementByTag.attr("href");
							if (StringUtils.isNotEmpty(aHref) && aHref.startsWith(post)) {
								urlPostList.add(aHref);
							}
						});
						break;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(urlPostList.size());

	}

}
