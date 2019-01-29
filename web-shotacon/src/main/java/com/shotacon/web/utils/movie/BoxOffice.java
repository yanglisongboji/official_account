package com.shotacon.web.utils.movie;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoxOffice {

    private String title;

    private String updateTime;

    private List<MovieMod> movieMods;

}
