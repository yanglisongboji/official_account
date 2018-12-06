package com.shotacon.wx.util.spider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

/**
 * Tumblr爬虫
 * 
 * @author shotacon
 *
 */
@Slf4j
public class TumblrSpiderUtil {

	private final static String video = "/video/";
	private final static String filePath = "/data/temp";
	private static String homeUrl;
	private static boolean proxy = false;

	public static void main(String[] args) throws IOException {
		doSpider("https://lookingforveins2.tumblr.com", 20);
	}

	public static String doSpider(String postUrl, int monthNum) {

		homeUrl = postUrl;
		List<String> monthList = new ArrayList<String>();
		monthList = TumblrUtil.getAllDateByMonth(monthNum);

		String userName = TumblrUtil.getUsernameByUrl(homeUrl);
		String parentFileName = TumblrUtil.getParentFile(filePath, userName);

		final CountDownLatch countDownLatch = new CountDownLatch(monthNum);
		ExecutorService es = Executors.newFixedThreadPool(monthNum);

		for (String month : monthList) {

			final String url = TumblrUtil.getUrl(homeUrl) + "archive/" + month;
			final String fileName = TumblrUtil.getFile(parentFileName, month);
			es.submit(new Runnable() {
				public void run() {
					try {
						Map<String, Set<String>> mediaList = handlePostList(getAllPostByUrl(url, month));
						getAllDownload(mediaList, fileName);
						countDownLatch.countDown();
					} catch (Exception e) {
						log.info("main execute exception url=" + url + ",error: " + e.getMessage());
					}
				}
			});
		}
		try {
			log.info("wait all process done");
			countDownLatch.await();
			es.shutdown();
		} catch (InterruptedException e) {
			log.error("ExecutorService shutdown error: {}", e.getMessage());
		}
		// 合并文件
		log.info("main merge files begin");
		String link = StringUtils.EMPTY;
		try {
			link = TumblrUtil.mergeFiles(parentFileName);
		} catch (IOException e) {
			log.error("mergeFiles error: {}", e.getMessage());
		}
		log.info("main execute end");
		return link;
	}

	/**
	 * 根据url获取所有post
	 * 
	 * @param url
	 * @param month
	 */
	static Set<String> getAllPostByUrl(String url, String month) {

		log.info("begin getAllPostByUrl ---> url: {}, month: {}", url, month);
		Set<String> urlPostList = new HashSet<String>();

		String yearMonth = month.replace("/", "");
		String post = TumblrUtil.getUrl(homeUrl) + "post/";
		try {
			String html = getHtml(url);
			Document doc = Jsoup.parse(html);
			Elements elements = doc.getElementsByTag("section");
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
		} catch (Exception e) {
			log.error("getAllPostByUrl error: {}", e.getMessage());
		}

		log.info("end getAllPostByUrl ---> url: {}, urlPostList size: {}", url, urlPostList.size());
		return urlPostList;
	}

	/**
	 * 根据post列表获取所有多媒体
	 * 
	 * @param urlPostList
	 * @return
	 */
	static Map<String, Set<String>> handlePostList(Set<String> urlPostList) {

		log.info("begin handlePostList with postList, size: {}", urlPostList.size());
		Set<String> urlVideoList = new HashSet<String>();
		Set<String> urlImageList = new HashSet<String>();

		for (String post : urlPostList) {
			String html;
			try {
				html = getHtml(post);
				int count = 1;
				log.info("handle the {}th url, src: {}", count, post);
				count++;
				Elements elements = Jsoup.parse(html).getElementsByTag("iframe");
				for (int i = 0; i < elements.size(); i++) {
					Element e = elements.get(i);
					String src = e.attr("src");
					if (StringUtils.isNotEmpty(src) && src.contains(video)) {
						urlVideoList.add(getDocByUrl(src).getElementsByTag("source").attr("src"));
						break;
					} else {
						Set<String> imageUrl = getImageUrl(post);
						if (imageUrl.size() > 0) {
							urlImageList.addAll(imageUrl);
							break;
						}
					}
				}
			} catch (Exception e) {
				log.error("handlePostList with {},  error: {}", post, e.getMessage());
			}
		}

		log.info("end handlePostList, urlVideoList size: {}", urlPostList.size());
		Map<String, Set<String>> result = new HashMap<>();
		result.put("video", urlVideoList);
		result.put("image", urlImageList);
		return result;
	}

	/**
	 * 获取连接中的图片
	 * 
	 * @throws Exception
	 * 
	 */
	public static Set<String> getImageUrl(String url) throws Exception {
		log.info("begin getImageUrl");
		Set<String> imageUrlStrList = new HashSet<>();

		Jsoup.parse(getHtml(url)).getElementsByClass("posts").forEach(posts -> {
			imageUrlStrList.addAll(
					posts.getElementsByTag("img").stream().map(e -> e.attr("src")).collect(Collectors.toList()));
		});
		// photo-wrapper
		Elements elementsByClass = Jsoup.parse(getHtml(url)).getElementsByClass("photo-wrapper-inner");
		if (null != elementsByClass) {
			imageUrlStrList.add(elementsByClass.get(0).getElementsByTag("img").get(0).attr("src"));
		} else {
			Elements elementsBytag = Jsoup.parse(TumblrSpiderUtil.getHtml(url)).getElementsByTag("img");
			imageUrlStrList.addAll(elementsBytag.stream().map(e -> e.attr("src")).collect(Collectors.toList()));
		}

		log.info("end getImageUrl with {} pic", imageUrlStrList.size());
		return imageUrlStrList;
	}

	private static void getAllDownload(Map<String, Set<String>> mediaList, String fileName) {
		log.info("begin getAllDownload");
		Set<String> videoList = mediaList.get("video");
		Set<String> imageList = mediaList.get("image");

		if (videoList.size() != 0) {
			log.info("write video url");
			Path videoPath = Paths.get(fileName + "_video.txt");
			if (!Files.exists(videoPath)) {
				log.info("Path not exists, create!");
				try {
					Files.createFile(videoPath);
				} catch (IOException e) {
					log.error("File create error! file path: {}", videoPath);
				}
			}
			try {
				Files.write(Files.exists(videoPath) ? videoPath : Files.createFile(videoPath), videoList,
						StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("write {} error, {}", videoPath, e.getMessage());
			}
		}
		if (imageList.size() != 0) {
			log.info("write image url");
			Path imagePath = Paths.get(fileName + "_image.txt");
			if (!Files.exists(imagePath)) {
				log.info("Path not exists, create!");
				try {
					Path createFile = Files.createFile(imagePath);
					log.info(Files.exists(createFile) ? "true" : "false");
				} catch (IOException e) {
					log.error("File create error! file path: {}", imagePath);
				}
			}
			try {
				Files.write(Files.exists(imagePath) ? imagePath : Files.createFile(imagePath), imageList,
						StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("write {} error, {}", imagePath, e.getMessage());
			}
		}

	}

	static String getHtml(String strUrl) throws Exception {
		String str = null;
		if (proxy) {
			String proxyHost = "127.0.0.1";
			int proxyPort = 1080;

			SystemDefaultCredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();
			credentialsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
					new UsernamePasswordCredentials("", ""));
			CloseableHttpClient httpClient = HttpClientBuilder.create()
					.setDefaultCredentialsProvider(credentialsProvider).setSSLSocketFactory(ssl()).build();

			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			HttpGet getMethod = new HttpGet(strUrl);
			getMethod.setConfig(config);
			CloseableHttpResponse rsp = httpClient.execute(getMethod);
			str = EntityUtils.toString(rsp.getEntity());
		} else {
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(ssl()).build();
			HttpGet getMethod = new HttpGet(strUrl);
			CloseableHttpResponse rsp = httpClient.execute(getMethod);
			str = EntityUtils.toString(rsp.getEntity());
		}

		return str;
	}

	private static SSLConnectionSocketFactory ssl()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		return sslsf;
	}

	/**
	 * 获取链接，并且处理异常
	 *
	 * @param url 待获取的链接
	 * @return
	 */
	static Document getDocByUrl(String url) {
		Connection connect = Jsoup.connect(url);
		try {
			return connect.get();
		} catch (IOException e) {
			log.error("Jsoup get connection error, {}", e.getMessage());
		}
		return null;
	}
}
