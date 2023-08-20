package com.flag.flag_back.Dto;

import java.util.Comparator;

public class CandidateResComparator implements Comparator<CandidateRes> {

    @Override
    public int compare(CandidateRes c1, CandidateRes c2) {
        // 인원 수가 많은 순
        int sizeCompare = Integer.compare(c2.getCandidates().size(), c1.getCandidates().size());
        if (sizeCompare != 0) {
            return sizeCompare;
        }

        // 인원 수가 같다면 긴 시간 순
        int timeCompare = Integer.compare(
                c2.getTimeSize(),
                c1.getTimeSize()
        );
        if (timeCompare != 0) {
            return timeCompare;
        }

        // 날짜가 빠른 순
        return c1.getDate().compareTo(c2.getDate());
    }
}
