package com.flag.flag_back.Model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor

@Data
@Table(name = "FlagTB")
@Entity
@Getter
@Setter
public class Flag {
    @Id
    @Column(name = "flagId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "flagName")
    private String name;
    @Column(name = "startTime")
    private String sTime;
    @Column(name = "endTime")
    private String eTime;
    @Column(name = "cycle")
    private String cycle;
    @Column(name = "userId")
    private String userId;

    @Builder
    public Flag(Long id,String name, String sTime, String eTime, String cycle, String userId) {
        this.id = id;
        this.name = name;
        this.sTime = sTime;
        this.eTime = eTime;
        this.cycle = cycle;
        this.userId = userId;
    }
}
