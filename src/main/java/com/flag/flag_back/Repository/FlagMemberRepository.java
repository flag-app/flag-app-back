package com.flag.flag_back.Repository;

import com.flag.flag_back.Model.FlagMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagMemberRepository extends JpaRepository<FlagMember, Long> {
}
