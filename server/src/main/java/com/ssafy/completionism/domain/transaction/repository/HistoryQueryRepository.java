package com.ssafy.completionism.domain.transaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.api.controller.transaction.response.HistoryResponse;
import com.ssafy.completionism.domain.transaction.History;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.ssafy.completionism.domain.transaction.QHistory.history;

@Repository
public class HistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HistoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<HistoryResponse> getHistoryResponseForPeriod(String loginId, HistoryPeriodSearchCond cond) {

        return queryFactory.select(constructor(HistoryResponse.class,
                        history.createdDate,
                        history.income,
                        history.outcome,
                        history.feel))
                .from(history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(cond.getStartDay(), cond.getEndDay()))
                .orderBy(history.createdDate.asc())
                .fetch();
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
