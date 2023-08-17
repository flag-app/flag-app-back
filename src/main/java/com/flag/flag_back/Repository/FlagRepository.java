package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlagRepository extends JpaRepository<Flag, Long> {
    Optional<Flag> findById(Long id);


    @Query("SELECT distinct f, u FROM Flag f LEFT JOIN UserFlagManager u ON f.id = u.flag.id WHERE u.user.userId =:id and f.state = true")
    List<Flag> findFlagByState(@Param("id") Long id);
//    @Query("SELECT distinct f FROM Flag f, UserFlagManager u WHERE u.user.userId =:id and f.state = true")
//    List<Flag> findFlagByState(@Param("id") Long id);

    @Query("SELECT distinct f, u FROM Flag f LEFT JOIN UserFlagManager u ON f.id = u.flag.id WHERE u.user.userId =:id and f.state = false")
    List<Flag> findFlagByState2(@Param("id") Long id);
}
