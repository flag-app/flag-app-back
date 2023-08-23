package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.FriendName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendNameRepository extends JpaRepository<FriendName, Long> {
    @Query("SELECT userName, friendName FROM FriendName")
    List<FriendName> findFriendAll();
}
