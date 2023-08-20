package com.flag.flag_back.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MyPageRes {

    private String email;
    private String name;
    private List<String> friends;

    @Builder
    public MyPageRes(String email, String name, List<String> friends) {
        this.email = email;
        this.name = name;
        this.friends = friends;
    }
}
