package com.ssafy.completionism.domain.transaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.api.controller.transaction.response.DiaryResponse;
import com.ssafy.completionism.api.controller.transaction.response.HistoryResponse;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.api.controller.transaction.response.SumIncomeOutcomeResponse;
import com.ssafy.completionism.api.service.transaction.dto.OneMonthIncomeExpenseDto;
import com.ssafy.completionism.domain.transaction.History;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.ssafy.completionism.domain.transaction.QHistory.history;
import static com.ssafy.completionism.domain.transaction.QTransaction.transaction;

@Repository
public class HistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HistoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TransactionResponse> getTransactionList(String loginId, LocalDate date) {

        List<Long> ids = queryFactory.select(transaction.id)
                .from(transaction)
                .join(transaction.history, history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay()))
                .orderBy(transaction.createdDate.desc())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory.select(constructor(TransactionResponse.class,
                        transaction.id,
                        transaction.time,
                        transaction.cost,
                        transaction.plus,
                        transaction.category,
                        transaction.place,
                        transaction.diary,
                        transaction.feel))
                .from(transaction)
                .where(transaction.id.in(ids))
                .fetch();
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

    public List<DiaryResponse> getDiaryResponse(String loginId) {
        return queryFactory.select(constructor(DiaryResponse.class,
                        history.id,
                        history.diary))
                .from(history)
                .where(history.member.loginId.eq(loginId))
                .orderBy(history.createdDate.asc())
                .fetch();
    }

    public Optional<DiaryResponse> getRegisteredDiary(String loginId, LocalDate transactionTime) {
        return Optional.ofNullable(queryFactory.select(constructor(DiaryResponse.class,
                        history.id,
                        history.diary,
                        history.feel))
                .from(history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(transactionTime.atStartOfDay(), transactionTime.plusDays(1).atStartOfDay()))
                .fetchFirst());
    }
    public Optional<SumIncomeOutcomeResponse> getSumIncomeOutcomeForPeriod(String loginId, HistoryPeriodSearchCond cond) {
        return Optional.ofNullable(queryFactory.select(constructor(SumIncomeOutcomeResponse.class,
                        history.income.sum(),
                        history.outcome.sum()
                ))
                .from(history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(cond.getStartDay(), cond.getEndDay()))
                .groupBy(history.member)
                .fetchOne());
    }

    public Optional<OneMonthIncomeExpenseDto> getOneMonthIncomeExpense(String loginId, LocalDateTime resultDate) {
        return Optional.ofNullable(queryFactory.select(constructor(OneMonthIncomeExpenseDto.class,
                        history.income.sum(),
                        history.outcome.sum()))
                .from(history)
                .where(history.member.loginId.eq(loginId),
                        history.createdDate.between(resultDate.minusDays(1).minusMonths(1), resultDate.minusDays(1)))
                .groupBy(history.createdDate.year(), history.createdDate.month())
                .fetchOne());
    }
}
