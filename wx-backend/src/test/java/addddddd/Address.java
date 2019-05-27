package addddddd;

import java.io.BufferedWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import lombok.Builder;
import lombok.Data;

public class Address {
	private static final Logger logger = LoggerFactory.getLogger(Address.class);

	private final static int ThreadNum = 10;
	private final static int timeout = 50000;
	private final static String fixUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
	private final static String baseUrl = "index.html";
	private static List<AdministrativeRegion> resultList = new ArrayList<>();
	private final static ExecutorService executor = Executors.newFixedThreadPool(ThreadNum);
	private final static CountDownLatch cdl = new CountDownLatch(ThreadNum);

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("Begin at {}", start);
		province();
		cdl.await(15, TimeUnit.MINUTES);
		executor.shutdown();
		logger.info(JSON.toJSONString(resultList));
		Path path = Paths.get("/data/AdministrativeRegion.txt");
		if (Files.notExists(path)) {
			Files.createFile(path);
		}
		BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.forName("UTF-8"),
				StandardOpenOption.APPEND);
		resultList.forEach(t -> {
			try {
				bufferedWriter.write(t.toString() + "/n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		long end = System.currentTimeMillis();
		logger.info("End at {}, cost {}", start, end - start);
	}

	public static void province() throws Exception {
		Document doc = connect(baseUrl);
		Elements firstLevelElements = doc.getElementsByClass("provincetr");
		List<Element> list = new ArrayList<>();
		firstLevelElements.forEach(elements -> {
			list.addAll(elements.getElementsByTag("a"));
		});
		list.forEach(element -> {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					Elements aTagElements = element.getElementsByTag("a");
					if (aTagElements.size() > 0) {
						String hrefStr = element.attr("href");
						String[] split = hrefStr.split("/");
						String code = split[0].split("[.]")[0];
						add(code, null, aTagElements.get(0).text(), null);
						try {
							city(hrefStr, code);
						} catch (Exception e) {
							logger.error("", e);
						}
					}
					cdl.countDown();
					logger.info("Thread {} is done", Thread.currentThread().getName());
				}
			});

		});
	}

	public static void city(String url, String parentCode) throws Exception {
		Document doc = connect(url);
		Elements classElements = doc.getElementsByClass("citytr");
		classElements.forEach(element -> {
			Elements aElements = element.getElementsByTag("a");
			if (aElements.size() > 0) {
				String href = aElements.get(0).attr("href");
				String perfix = href.split("/")[0];
				String _parentCode = aElements.get(0).text();
				add(_parentCode, null, aElements.get(1).text(), parentCode);
				try {
					county(href, perfix, _parentCode);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		});
	}

	// http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/11/01/110102.html
	public static void county(String url, String perfix, String parentCode) throws Exception {
		Document doc = connect(url);
		Elements countryElements = doc.getElementsByClass("countytr");
		countryElements.forEach(element -> {
			Elements aElements = element.getElementsByTag("td");
			if (aElements.size() > 0) {
				Elements aEles = aElements.get(0).getElementsByTag("a");
				if (aEles.size() <= 0) {
					add(aElements.get(0).text(), null, aElements.get(1).text(), parentCode);
				} else {
					String href = aEles.attr("href");
					String _perfix = perfix + "/" + href.split("/")[0];
					String _parentCode = aElements.get(0).text();
					add(_parentCode, null, aElements.get(1).text(), parentCode);
					try {
						town(perfix + "/" + aEles.attr("href"), _perfix, _parentCode);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			}
		});
	}

	public static void town(String url, String perfix, String parentCode) throws Exception {
		Document doc = connect(url);
		Elements countryElements = doc.getElementsByClass("towntr");
		countryElements.forEach(element -> {
			Elements aElements = element.getElementsByTag("a");
			if (aElements.size() > 0) {
				String _parentCode = aElements.get(0).text();
				add(_parentCode, null, aElements.get(1).text(), parentCode);
				try {
					village(perfix + "/" + aElements.get(0).attr("href"), _parentCode);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		});
	}

	public static void village(String url, String parentCode) throws Exception {
		Document doc = connect(url);
		Elements countryElements = doc.getElementsByClass("villagetr");
		countryElements.forEach(element -> {
			Elements aElements = element.getElementsByTag("td");
			if (aElements.size() > 0) {
				add(aElements.get(0).text(), aElements.get(1).text(), aElements.get(2).text(), parentCode);
			}
		});
	}

	private static void add(String code, String classCode, String name, String parentCode) {
		AdministrativeRegion build = AdministrativeRegion.builder().code(code).classCode(classCode).name(name)
				.parentCode(parentCode).build();
		synchronized (resultList) {
			resultList.add(build);
		}
	}

	private static Document connect(String url) throws Exception {
		Thread.sleep(2000);
		String u = fixUrl + url;
		Document parse = null;

		for (int i = 0; i < 5; i++) {
			try {
				logger.info("第{}次请求url: {}", i, u);
				parse = Jsoup.connect(u).timeout(timeout).get();
				if (null != parse) {
					break;
				}
			} catch (Exception e) {
				logger.error("第{}次连接:{}, 异常信息:{}", i, u.toString(), e);
			}
		}
		return parse;
	}

	@Data
	@Builder
	static class AdministrativeRegion {
		String code;
		String name;
		String classCode;
		String parentCode;

		@Override
		public String toString() {
			return parentCode + "---" + code + "---" + name + "---" + classCode;
		}

	}

}
