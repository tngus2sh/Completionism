package com.ssafy.completionism.domain.member.repository;

import com.ssafy.completionism.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByPhone(String phone);
}
