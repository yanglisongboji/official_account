package com.shotacon.web.utils.movie;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;

import com.shotacon.web.config.RestSSLClient;

public class Mtime {

    public static BoxOffice dailyBoxOffice() {

        String url = "http://movie.mtime.com/boxoffice/#CN/daily";
        BoxOffice boxOffice = new BoxOffice();
        List<MovieMod> mmList = new ArrayList<>();

        ResponseEntity<String> responseEntity = RestSSLClient.httpRestTemplate.getForEntity(url, String.class);

        String body = responseEntity.getBody();

        Document doc = Jsoup.parse(body);

        Element bocontent = doc.getElementsByClass("bocontent").get(0);

        // 获取更新时间和标题
        Element typetitle = bocontent.getElementsByClass("typetitle").get(0);
        Element updateday = typetitle.getElementsByClass("updateday").get(0);
        String[] updateTimeSplit = updateday.childNodes().get(0).toString().split("更新时间：");

        boxOffice.setUpdateTime(updateTimeSplit.length >= 2 ? updateTimeSplit[1] : "");
        boxOffice.setTitle(typetitle.childNodes().get(0).childNode(0).toString());

        Elements dds = bocontent.getElementsByClass("boxofficelist").get(0).getElementsByTag("dd");

        for (Element e : dds) {
            MovieMod mm = new MovieMod();
            Element picPart = e.getElementsByClass("picbox").get(0);
            // 获取rank
            mm.setRank(picPart.childNode(0).childNode(0).toString());

            // 获取海报
            mm.setPostUrl(picPart.getElementsByAttribute("src").get(0).attr("src"));

            Element txtPart = e.getElementsByClass("txtbox").get(0);

            // 获取中英文名
            Elements cnNames = txtPart.getElementsByTag("h3");
            Elements enNames = txtPart.getElementsByTag("h4");
            String nameCN = cnNames.size() > 0 ? cnNames.get(0).childNode(0).childNode(0).toString() : "";
            String nameEN = enNames.size() > 0 ? enNames.get(0).childNode(0).childNode(0).toString() : "";
            mm.setNameCN(nameCN);
            mm.setNameEN(nameEN);

            // 获取上映信息
            Elements dataDays = txtPart.getElementsByClass("dataday");
            int p = 0;
            if (dataDays.size() > 0) {
                Elements data = dataDays.get(0).getAllElements();
                mm.setDataDay(data.get(0).text());
                p++;
            }
            mm.setReleaseInfo(txtPart.getElementsByTag("p").get(p).text());

            // 获取剧组信息
            StringBuffer sb = new StringBuffer();
            txtPart.getElementsByTag("b").get(0).getElementsByTag("p")
                    .forEach(element -> sb.append(element.text()).append("\n"));
            mm.setCrewInfo(sb.toString());

            // 获取评分
            Elements gradebox = e.getElementsByClass("gradebox").get(0).getElementsByClass("point");
            mm.setPoint(gradebox.size() > 0 ? gradebox.get(0).text() : "");

            // 获取总票房
            mm.setTotalNum(e.getElementsByClass("totalnum").get(0).text());
            // 获取当日票房
            mm.setDayNum(e.getElementsByClass("totalnum").get(1).text());

            mmList.add(mm);
        }

        boxOffice.setMovieMods(mmList);
        return boxOffice;
    }
}
