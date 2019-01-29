package com.shotacon.web.utils.movie;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MovieMod {

    /**
     * 中文名
     */
    private String nameCN;

    /**
     * 英文名
     */
    private String nameEN;

    /**
     * 海报图
     */
    private String postUrl;

    /**
     * 排名
     */
    private String rank;

    /**
     * 首日票房
     */
    private String dataDay;

    /**
     * 上映信息
     */
    private String releaseInfo;

    /**
     * 剧组信息
     */
    private String crewInfo;

    /**
     * 日票房
     */
    private String dayNum;

    /**
     * 总票房
     */
    private String totalNum;

    /**
     * 评分
     */
    private String point;
}
