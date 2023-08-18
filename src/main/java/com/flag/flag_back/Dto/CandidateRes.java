package com.flag.flag_back.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CandidateRes {

    private String date;
    private String startTime;
    private String endTime;
    private List<String> candidates;

    public CandidateRes(String date, String startTime, String endTime, List<String> candidates) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.candidates = candidates;
    }
}
