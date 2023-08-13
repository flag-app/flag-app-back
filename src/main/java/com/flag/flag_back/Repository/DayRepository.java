package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
}
