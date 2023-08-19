package com.flag.flag_back.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Data
@Table(name = "DayTB")
@Entity
@Getter
@Setter
public class Day {
    @Id
    @Column(name = "dayId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "day", fetch = LAZY)
    private UserFlagManager userFlagManager;

    @ElementCollection
    private List<String> date;

    @ElementCollection
    private List<Boolean> days = new ArrayList<>(Collections.nCopies(100, false));

    public Day(UserFlagManager userFlagManager, List<String> date) {
        System.out.println(this.getId());
        this.userFlagManager = userFlagManager;
        this.date = date;
    }

    public void setSchedule(List<Integer> possibleDates) {
        for (int index : possibleDates) {
            this.days.set(index, true);
        }
    }

    public boolean getSchedule(int index) {
        return this.days.get(index);
    }
}