package com.ssafy.completionism.domain.budget.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.ssafy.completionism.domain.budget.QBudget.budget;

@Repository
public class BudgetQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BudgetQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 전체 매월 목표예산 출력
     * @param memberId 사용자 식별키
     * @return 매달 목표예산 리스트
     */
    public List<MonthBudgetResponse> getMonthBudgetAll(Long memberId) {
        return queryFactory.select(constructor(MonthBudgetResponse.class,
                budget.id,
                budget.member.id,
                budget.yearMonth,
                budget.totalBudget,
                budget.category
                ))
                .from(budget)
                .where(budget.member.id.eq(memberId))
                .fetch();
    }

    /**
     * 해당기간 매월 목표예산 출력
     * @param memberId 사용자 식별키
     * @param startMonth 기간 시작
     * @param endMonth 기간 끝
     * @return 해당 기간 목표예산 리스트
     */
    public List<MonthBudgetResponse> getMonthBudget(Long memberId, LocalDate startMonth, LocalDate endMonth) {
        return queryFactory.select(constructor(MonthBudgetResponse.class,
                budget.id,
                budget.member.id,
                budget.yearMonth,
                budget.totalBudget,
                budget.category
                ))
                .from(budget)
                .where(budget.member.id.eq(memberId),
                        budget.yearMonth.between(startMonth, endMonth))
                .fetch();
    }

}
