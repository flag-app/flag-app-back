package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlagRepository extends JpaRepository<Flag, Long> {
    Optional<Flag> findById(Long id);
}
