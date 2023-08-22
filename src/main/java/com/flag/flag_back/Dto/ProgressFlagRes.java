package com.flag.flag_back.Dto;

import lombok.Data;

@Data
public class ProgressFlagRes {

    Long id;
    private String title;
    private String name;
    private String place;
    private String host;
    private int count;

    public ProgressFlagRes(Long id, String name, String place, String host, int count) {
        this.id = id;
        this.title = "progress";
        this.name = name;
        this.place = place;
        this.host = host;
        this.count = count;
    }
}
