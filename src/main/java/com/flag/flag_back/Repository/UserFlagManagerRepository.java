package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.UserFlagManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFlagManagerRepository extends JpaRepository<UserFlagManager, Long> {
}
