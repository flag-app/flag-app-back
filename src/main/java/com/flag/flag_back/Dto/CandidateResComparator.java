package com.flag.flag_back.Dto;

import java.util.Comparator;

public class CandidateResComparator implements Comparator<CandidateRes> {

    @Override
    public int compare(CandidateRes c1, CandidateRes c2) {
        // Compare by candidates.size() in descending order
        int sizeCompare = Integer.compare(c2.getCandidates().size(), c1.getCandidates().size());
        if (sizeCompare != 0) {
            return sizeCompare;
        }

        // Compare by (endTime - startTime) in descending order
        int timeCompare = Integer.compare(
                c2.getTimeSize(),
                c1.getTimeSize()
        );
        if (timeCompare != 0) {
            return timeCompare;
        }

        // Compare by date in ascending order
        return c1.getDate().compareTo(c2.getDate());
    }
}
