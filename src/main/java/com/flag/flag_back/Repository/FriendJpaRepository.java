package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FriendJpaRepository extends JpaRepository<Friend, Long> {
    List<Friend> findUserEntityByUserId(Long userId1);
    Friend findUserEntityByUserIdAndUserId2(Long userId, Long userId2);
    @Query(value = "select DISTINCT c from Friend c")
    List<Friend> findByUserId(Long id);
}
