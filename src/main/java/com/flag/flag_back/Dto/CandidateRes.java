package com.flag.flag_back.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CandidateRes {

    private String date;
    private int startCellIndex;
    private int endCellIndex;
    private List<Long> candidates;

    public CandidateRes(String date, int startCellIndex, int endCellIndex, List<Long> candidates) {
        this.date = date;
        this.startCellIndex = startCellIndex;
        this.endCellIndex = endCellIndex;
        this.candidates = candidates;
    }
}
