package com.shotacon.wx.util.spider;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	public static void main(String[] args) throws Exception {

		String basePath = "/Users/shotacon/Downloads/down/123/213";
		doError(basePath);

	}

	public static void doError(String basePath) throws IOException {
		Files.walk(Paths.get(basePath), FileVisitOption.FOLLOW_LINKS)
				.filter(path -> path.getFileName().toString().contains("error")).forEach(path -> {
					try {
						System.out.println(path);
						Set<String> imgList = Files.list(path.getParent()).map(a -> a.getFileName().toString())
								.collect(Collectors.toSet());

						Set<String> collect = new HashSet<>();
						Files.lines(path).collect(Collectors.toSet()).stream().forEach(p -> {
							String[] split = p.split("/");
							String fileName = split[split.length - 1];
							if (!imgList.contains(fileName)) {
								collect.add(p);
							}
						});

						Files.write(path, collect);
						System.out.println("done with " + collect.size());

					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	public static void doFile(String basePath) throws IOException {
		String path = "/b-a-dboy_image";
		Set<Path> collect = Files.list(Paths.get(basePath + path))
				.filter(file -> file.getFileName().toString().contains("(1)")).collect(Collectors.toSet());
		collect.forEach(path1 -> {
			try {
				Files.deleteIfExists(path1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
