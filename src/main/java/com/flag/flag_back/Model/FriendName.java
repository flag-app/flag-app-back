package com.flag.flag_back.Model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor

@Data
@Table(name = "FriendNameTB")
@Entity
@Getter
@Setter
public class FriendName {
    @Id
    @Column(name = "fid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "friendname")
    private String friendName;

    @Builder
    public FriendName(Long id, String userName, String friendName) {
        this.id = id;
        this.userName = userName;
        this.friendName = friendName;
    }
}
