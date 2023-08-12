package com.flag.flag_back.Model;
import lombok.*;
import javax.persistence.*;
@NoArgsConstructor
@Data
@Table(name = "FlagMember")
@Entity
@Getter
@Setter
public class FlagMember {
    @Id
    @Column(name = "flagMemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId1")
    private Long userId;
    @Column(name = "flagId1")
    private Long flagId;
    @Column(name = "status")
    private boolean status;
    @Builder
    public FlagMember(Long id, Long userId, Long flagId, boolean status) {
        this.id = id;
        this.userId = userId;
        this.flagId = flagId;
        this.status = status;
    }
}