package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.Friend;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendDto {
    @Column(name = "userId")
    private Long userId1;
    @Column(name = "friendUserId")
    private Long userId2;

    public Friend toEntity() {
        Friend friend = Friend.builder()
                .userId1(userId1)
                .userId2(userId2)
                .build();
        return friend;
    }

    @Builder
    public FriendDto(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
}
