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
    @Column(name = "userId")
    private Long userId;
    @Column(name = "flagFriendId")
    private Long flagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    @JoinColumn(name = "flagId")
    Flag flag;
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