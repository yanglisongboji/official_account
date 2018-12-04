package com.shotacon.wx.util.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpiderUtil {

	/**
	 * 获取链接，并且处理异常
	 *
	 * @param url 待获取的链接
	 * @return
	 */
	public static Document getDocByUrl(String url) {
		Connection connect = Jsoup.connect(url);
		try {
			return connect.get();
		} catch (IOException e) {
			log.error("Jsoup get connection error, {}", e.getMessage());
		}
		return null;
	}

	/**
	 * 获取图片链接列表
	 * 
	 * @param document 等地啊分析的文档
	 * @return
	 */
	public static List<String> getImageUrlList(Document document) {
		List<String> imageUrlStrList = new ArrayList<>();
		Elements elements = document.getElementsByTag("img");
		for (Element element : elements) {

			String imageUrlStr = element.attr("data-highres");
			if (imageUrlStr != null && imageUrlStr != "") {
				imageUrlStrList.add(imageUrlStr);
			}
		}
		return imageUrlStrList;
	}

	public static List<String> crawlVideoUrl(Document doc) {
		List<String> videoCrawlUrlList = crawlNeedCrawlVideoUrl(doc);
		List<String> videoUrlList = new ArrayList<>();
		// 真正的视频下载链接
		for (String videoCrawlUrl : videoCrawlUrlList) {
			Document videoDoc = getDocByUrl(videoCrawlUrl);
			if (videoDoc != null) {
				Elements videoElements = videoDoc.getElementsByTag("source");
				for (Element videoElement : videoElements) {

					String videoUrl = videoElement.attr("src");
					videoUrlList.add(videoUrl);
					System.out.println("爬取的视频链接地址： " + videoUrl);
				}
			}
		}
		return videoUrlList;
	}

	/**
	 * 抓去需要爬去的视频链接的链接
	 *
	 * @param doc
	 * @return
	 */
	public static List<String> crawlNeedCrawlVideoUrl(Document doc) {

		List<String> videoCrawlUrlList = new ArrayList<>();
		// 爬图片和视频的链接
		Elements videoCrawlElements = doc.getElementsByTag("iframe");
		// 视频预处理
		for (Element element : videoCrawlElements) {
			String videoCrawlUrl = element.attr("src");
			// 通过正则过滤，判断是否为视频链接
			String pattern = "^.*video.*$";
			boolean isMatch = Pattern.matches(pattern, videoCrawlUrl);
			if (isMatch) {
				videoCrawlUrlList.add(videoCrawlUrl);
			}
		}
		return videoCrawlUrlList;
	}
}
