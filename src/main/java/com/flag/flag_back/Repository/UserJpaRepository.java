package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
