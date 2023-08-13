package com.flag.flag_back.Model;
import lombok.*;
import javax.persistence.*;
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
    @Column(name = "date")
    private String date;
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    @JoinColumn(name = "flagId")
    Flag flag;
    @Builder
    public Day(long id, String date) {this.id = id;this.date=date;}
}