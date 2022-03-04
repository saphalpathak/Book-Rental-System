package com.wicc.brs.repo;

import com.wicc.brs.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member,Integer> {

}
