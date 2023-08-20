package com.flag.flag_back.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CandidateRes {

    private String date;
    private String startTime;
    private String endTime;
    private int timeSize;
    private List<String> candidates;

    public CandidateRes(String date, String startTime, String endTime, int timeSize, List<String> candidates) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSize = timeSize;
        this.candidates = candidates;
    }
}
