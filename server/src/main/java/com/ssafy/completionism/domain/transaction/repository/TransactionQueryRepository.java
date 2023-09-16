package com.ssafy.completionism.domain.transaction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.domain.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.ssafy.completionism.domain.transaction.QTransaction.transaction;

@Repository
public class TransactionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TransactionQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TransactionResponse> getTransactionsOneDay(Member member, LocalDateTime resultDate) {
        return queryFactory.select(constructor(TransactionResponse.class,
                        transaction.time,
                        transaction.cost,
                        transaction.plus,
                        transaction.category,
                        transaction.place))
                .from(transaction)
                .where(transaction.history.member.eq(member),
                        transaction.createdDate.between(resultDate.minusDays(1), resultDate))
                .orderBy(transaction.cost.desc())
                .fetch();
    }

}
