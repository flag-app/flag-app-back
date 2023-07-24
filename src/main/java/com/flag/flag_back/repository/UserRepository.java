package com.flag.flag_back.repository;

import com.flag.flag_back.dto.UserInfo;
import com.flag.flag_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 정보 조회
    List<User> findUserEntityByEmail(String email);
    List<User> findUserEntityByName(String name);
    UserInfo findUserById(Long id);
    User findUserEntityByEmailAndPassword(String email, String name);
}
