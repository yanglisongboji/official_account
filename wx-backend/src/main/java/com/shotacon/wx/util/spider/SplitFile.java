package com.shotacon.wx.util.spider;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

public class SplitFile {

	public static void main(String[] args) throws Exception {
		String filePath = "/Users/shotacon/Downloads/down/";
		String fileName = "psycob0y_image.txt";
//		doImg(String filePath, String fileName);
		cleanImg(filePath, fileName);

	}

	private static void cleanImg(String filePath, String fileName) throws IOException {

		Files.list(Paths.get(filePath)).forEach(path -> {
			if (path.getFileName().toString().contains("image") && !Files.isDirectory(path)) {
				try {
					doImg(filePath, path.getFileName().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		;
	}

	private static void doImg(String filePath, String fileName) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(filePath + fileName)).stream()
				.filter(line -> !line.contains("px.srvcs.tumblr.com")).collect(Collectors.toList());

		System.out.println(lines.size());

		for (int i = 0; i <= lines.size() / 1000; i++) {

			List<String> collect = lines.stream().skip(i * 1000).limit(1000).collect(Collectors.toList());

			Files.write(Paths.get(filePath + fileName.replace("image", "image" + i)), collect, StandardCharsets.UTF_8);
		}

		System.out.println("done with " + fileName);
	}

}
