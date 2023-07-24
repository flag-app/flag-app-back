package com.flag.flag_back.repository;

import com.flag.flag_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {

}
