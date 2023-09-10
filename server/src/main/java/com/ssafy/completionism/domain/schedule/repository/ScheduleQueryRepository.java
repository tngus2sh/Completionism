package com.ssafy.completionism.domain.schedule.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.completionism.api.controller.schedule.response.FutureScheduleResponse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.completionism.domain.schedule.QSchedule.schedule;

@Repository
public class ScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final DateTemplate<String> formatter =  Expressions.dateTemplate(String.class,
            "DATE_FORMAT({0}, {1})",
            schedule.date,
            ConstantImpl.create("%Y-%m-%d"));

    public ScheduleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<FutureScheduleResponse> getSchedules(String loginId, boolean fixed) {
        List<FutureScheduleResponse> futureSchedules = queryFactory
                .select(Projections.fields(FutureScheduleResponse.class,
                        schedule.id,
                        formatter.as("date"),
                        schedule.todo,
                        schedule.cost,
                        schedule.plus))
                .from(schedule)
                .where(schedule.member.loginId.eq(loginId), schedule.fixed.eq(fixed))
                .orderBy(
                        schedule.date.asc(),
                        schedule.todo.asc()
                )
                .fetch();

        return futureSchedules;
    }
}
