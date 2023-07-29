package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendJpaRepository extends JpaRepository<Friend, Long> {
    List<Friend> findUserEntityByUserId1(Long userId1);
    Friend findUserEntityByUserId1AndUserId2(Long userId1, Long userId2);
}
