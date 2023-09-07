package com.ssafy.completionism.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.domain.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.completionism.domain.member.QMember.member;


@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Member> getByLoginIdAndActive(String loginId, boolean active) {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.loginId.eq(loginId), member.active.isTrue())
                .fetchFirst();

        return Optional.ofNullable(findMember);

    }
}
