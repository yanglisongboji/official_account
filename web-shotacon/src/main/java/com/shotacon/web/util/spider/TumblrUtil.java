package com.shotacon.web.util.spider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TumblrUtil {

//	private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取month个月的日期格式：yyyy/M
     * 
     * @return
     */
    public static List<String> getAllDateByMonth(int num) {
        List<String> list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < num; i++) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            calendar.add(Calendar.MONTH, -1);
            String monthStr = month < 10 ? "0" + month : String.valueOf(month);
            list.add(year + "/" + monthStr);
        }
        return list;
    }

    public static long getFirstDayMillisecondsByMonth(String month) {
        String time = month.replace("/", "-") + "-01";
        Date parse = new Date();
        try {
            parse = sdf.parse(time);
        } catch (ParseException e) {
            log.error("Date format error");
        }
        return parse.getTime();
//		return LocalDateTime.parse(month.replace("/", "-") + "-01 00:00:00", dtf).toInstant(ZoneOffset.of("+8"))
//				.toEpochMilli();
    }

    public static List<String> getAllDateByMonthString(String monthStr) {
        List<String> list = new ArrayList<String>();
        for (String m : monthStr.split(",")) {
            list.add(m);
        }
        return list;
    }

    public static String getUsernameByUrl(String url) {
        if (url.startsWith("http://")) {
            url = url.substring(7);
        } else if (url.startsWith("https://")) {
            url = url.substring(8);
        }
        int index = url.indexOf(".");
        String username = url.substring(0, index);
        return username;
    }

    public static String getUrl(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        return url;
    }

    public static String getParentFile(String filePath, String userName) {
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }
        return filePath + userName;
    }

    public static String getFile(String parentFilePath, String yearMonth) {
        yearMonth = yearMonth.replace("/", "_");
        String fileName = parentFilePath + File.separator + yearMonth + ".txt";
        return fileName;
    }

    public static boolean createNewFile(File file) throws IOException {
        if (!file.exists()) {
            makeDir(file.getParentFile());
        }
        return file.createNewFile();
    }

    private static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public static String mergeFiles(String parentFilePath) throws IOException {
        Set<String> videoSet = new HashSet<String>();
        Set<String> imageSet = new HashSet<String>();
        Path parentpath = Paths.get(parentFilePath);
        if (!Files.exists(parentpath)) {
            Files.createDirectory(parentpath);
        }

        Files.list(parentpath).forEach(path -> {
            try {
                if (!path.getFileName().equals(parentpath.getFileName())) {
                    if (path.getFileName().toString().contains("image")) {
                        imageSet.addAll(Files.readAllLines(path));
                    } else {
                        videoSet.addAll(Files.readAllLines(path));
                    }
                }
            } catch (IOException e) {
                log.info("File {} readAllLines error: {}", path.getFileName(), e.getMessage());
            }
        });

        Path imageFile = Paths.get(parentFilePath + File.separator + parentpath.getFileName() + "_image.txt");
        Path videoFile = Paths.get(parentFilePath + File.separator + parentpath.getFileName() + "_video.txt");
        List<File> fileList = new ArrayList<>();
        fileList.add(Files.write(Files.exists(imageFile) ? imageFile : Files.createFile(imageFile), imageSet,
                StandardCharsets.UTF_8).toFile());
        fileList.add(Files.write(Files.exists(videoFile) ? videoFile : Files.createFile(videoFile), videoSet,
                StandardCharsets.UTF_8).toFile());

        // 上传
        return "succ";
    }

    public static void main(String[] args) throws IOException {
        mergeFiles("/data/temp/just-an-insane-boy");
    }
}
