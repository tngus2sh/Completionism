package com.ssafy.completionism.domain.transaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.domain.transaction.History;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.ssafy.completionism.domain.transaction.QHistory.history;

@Repository
public class HistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HistoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<History> getRegisteredHistory(String loginId, LocalDateTime transactionTime) {
        History findHistory = queryFactory
                .select(history)
                .from(history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(transactionTime, transactionTime.plusDays(1)))
                .fetchFirst();
        return Optional.ofNullable(findHistory);
    }

}
