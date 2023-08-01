package com.flag.flag_back.Model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor

@Data
@Table(name = "UserTB")
@Entity
@Getter
@Setter
public class User {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "userName")
    private String name;
    @Column(name = "userEmail")
    private String email;
    private String password;

    @Builder
    public User(Long id,String name, String email, String password) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
