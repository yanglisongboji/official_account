package com.shotacon.wx.util.spider;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class SplitFile {

    public static void main(String[] args) throws Exception {

//		readLinkList();
        String src = "https://cv.phncdn.com/videos/201901/29/204536041/720P_1500K_204536041.mp4?tnpBUpKNqyET1nS-Qm5j3R_5_0P_0ecLTHJTGVBmuR6VGk2Y19KQrwVnw0tFeTYUKAT9juggOD09h_KbOB_Pk02x8t_ofRLGmRl4_YY3T2UxU6H3dFk-CqC3zDD2UpvSSner18MXTiC_C37ot3jVh1i92VDvuDtqXTIBQlJsnoY0g3TnSEYMoJBLx3FWuoVlD6Ttu5G4ons";
        downloadVideo(src, "/data/temp");

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

    private static void readLinkList() throws IOException, InterruptedException {

        DateTimeFormatter dif = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String basePath = "/Users/shotacon/Downloads/down/";
        List<Path> fileList = Files.list(Paths.get(basePath))
                .filter(path -> !Files.isDirectory(path) && !path.getFileName().toString().contains("DS_Store"))
                .collect(Collectors.toList());

        int count = fileList.size() * 20;
        ExecutorService es = Executors.newFixedThreadPool(count);

        for (Path path : fileList) {

            Set<String> lines = Files.lines(path)
                    .filter(line -> !line.contains("px.srvcs.tumblr.com") && !line.contains("avatar_"))
                    .collect(Collectors.toSet());
            String dir = basePath + path.getFileName().toString().split("\\.")[0] + "/";
            Path imgPath = Paths.get(dir);
            if (!Files.exists(imgPath)) {
                Files.createDirectories(imgPath);
            }

            for (int i = 1; i <= count; i++) {

                Set<String> limit = lines.size() > count
                        ? lines.stream().skip(i - 1).limit(lines.size() / count * i).collect(Collectors.toSet())
                        : lines.stream().collect(Collectors.toSet());

                es.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (String src : limit) {
                            try {
                                System.out.println(Thread.currentThread() + " >>> " + LocalDateTime.now().format(dif)
                                        + " >>> " + imgPath + " >>>> " + src);
                                downloadVideo(src, dir);
                            } catch (Exception e) {
                                System.out.println(path.getFileName().toString());
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread() + " >>> " + LocalDateTime.now().format(dif)
                                    + " >>> " + imgPath + " >>>> " + src);
                        }
                    }
                });
                if (lines.size() <= count) {
                    break;
                }
            }

            System.out.println(path.getFileName().toString() + "done!");
        }
        System.out.println("close executor");
        es.shutdown();

    }

    private static void downloadImg(String src, String dir) throws IOException {

        String[] split = src.split("/");
        String fileName = split[split.length - 1];

        try {
            if (!Files.exists(Paths.get(dir)))
                Files.createDirectories(Paths.get(dir));
            if (Files.exists(Paths.get(dir + fileName)))
                return;
            Response response = Jsoup.connect(src).timeout(1000 * 60).ignoreContentType(true).execute();
            byte[] bodyAsBytes = response.bodyAsBytes();
            Files.write(Paths.get(dir + fileName), bodyAsBytes);
        } catch (Exception e) {
            Files.write(Paths.get(dir + "error1.txt"), Arrays.asList(src), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }

    }

    private static void downloadVideo(String src, String dir) throws IOException {

        String[] split = src.split("/");
        String fileName = split[split.length - 1];

        if (StringUtils.isNumeric(fileName)) {
            fileName = split[split.length - 2] + "_" + fileName + ".mp4";
        } else {
            fileName += ".mp4";
        }

        try {
            if (!Files.exists(Paths.get(dir)))
                Files.createDirectories(Paths.get(dir));
            if (Files.exists(Paths.get(dir + fileName)))
                return;
            Response response = Jsoup.connect(src).ignoreContentType(true).maxBodySize(Integer.MAX_VALUE)
                    .timeout(Integer.MAX_VALUE).execute();
            BufferedInputStream body = response.bodyStream();
            IOUtils.copyLarge(body, new FileOutputStream(dir + fileName));

        } catch (Exception e) {
            Files.write(Paths.get(dir + "error.txt"), Arrays.asList(src + " ---- " + e.getMessage()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }

    }

}
