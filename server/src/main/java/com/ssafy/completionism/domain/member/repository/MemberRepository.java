package com.ssafy.completionism.domain.member.repository;

import com.ssafy.completionism.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.refreshToken=:refreshToken where m.loginId=:loginId")
    Optional<Void> updateRefreshToken(@Param("loginId") String loginId, @Param("refreshToken") String refreshToken);
}
