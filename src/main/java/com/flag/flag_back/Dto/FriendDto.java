package com.flag.flag_back.Dto;

import com.flag.flag_back.Model.Friend;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendDto {

    private Long userId;

    private Long userId2;

    public Friend toEntity() {
        Friend friend = Friend.builder()
                .userId1(userId)
                .userId2(userId2)
                .build();
        return friend;
    }

    @Builder
    public FriendDto(Long userId, Long userId2) {
        this.userId = userId;
        this.userId2 = userId2;
    }
}
