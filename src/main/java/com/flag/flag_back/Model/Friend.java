package com.flag.flag_back.Model;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor

@Data
@Table(name = "FriendTB")
@Entity
@Getter
@Setter
public class Friend {
    @Id
    @Column(name = "fid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private Long userId1;
    @Column(name = "friendUserId")
    private Long userId2;

    @Builder
    public Friend(Long id, Long userId1, Long userId2) {
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

}
