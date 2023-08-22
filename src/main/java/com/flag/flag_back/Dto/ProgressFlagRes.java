package com.flag.flag_back.Dto;

import lombok.Data;

@Data
public class ProgressFlagRes {

    private String name;
    private String place;
    private String host;
    private int count;

    public ProgressFlagRes(String name, String place, String host, int count) {
        this.name = name;
        this.place = place;
        this.host = host;
        this.count = count;
    }
}
