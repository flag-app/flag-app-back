package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagRepository extends JpaRepository<Flag, Long> {
}
