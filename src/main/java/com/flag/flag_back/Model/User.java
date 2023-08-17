package com.flag.flag_back.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor

@Data
@Table(name = "UserTB")
@Entity
@Getter
@Setter
@JsonIgnoreProperties("user")
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

    private String profile;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserFlagManager> userFlagManagers = new ArrayList<>();

    @Builder
    public User(Long id,String name, String email, String password, String profile) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }
}
