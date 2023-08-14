package com.flag.flag_back.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class TotalDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "totalDay", fetch = LAZY)
    private Flag flag;

    @ElementCollection
    private List<String> date;

    @ElementCollection
    private List<Integer> days = new ArrayList<>(100);


}
