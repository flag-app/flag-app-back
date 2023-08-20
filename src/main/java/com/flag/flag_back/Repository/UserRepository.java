package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 정보 조회
    List<User> findUserEntityByEmail(String email);

    List<User> findUserEntityByName(String name);

    User findUserByEmail(String email);
    User findUserByName(String name);


    User findUserEntityByUserId(Long id);

    List<User> findUserEntityByPassword(String password);

    User findUserEntityByEmailAndPassword(String email, String password);

    //@EntityGraph(attributePaths = "friendsList")
    @Query("SELECT u, f FROM User u LEFT JOIN Friend f ON u.userId = f.userId2 WHERE f.userId =:id")
    //@Query(value = "select DISTINCT c from User c left join Friend f where f.userId = c.userId AND f.userId2 = c.userId")
    List<User> findFriendListByUserId(@Param("id") Long id);

    @Query("SELECT u, f FROM User u LEFT JOIN Friend f ON u.userId = f.userId2 WHERE f.userId =:id and u.name = :name")
    User findFriendListByName(@Param("id") Long id, @Param("name") String name);
}
