package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 정보 조회
    List<User> findUserEntityByEmail(String email);
    List<User> findUserEntityByName(String name);
    User findUserEntityByEmailAndPassword(String email, String name);
}
