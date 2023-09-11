package com.ssafy.completionism.domain.schedule;

import com.ssafy.completionism.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column
    private LocalDate date;

    @Column(nullable = false)
    private String todo;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private boolean plus;

    @Column(nullable = false)
    private boolean fixed;

    @Column(name = "period_type")
    private boolean periodType;

    @Column
    private int period;


    @Builder
    private Schedule(Long id, Member member, LocalDate date, String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
        this.periodType = periodType;
        this.period = period;
    }

    public static Schedule toFutureSchedule(Member member, LocalDate date, String todo, int cost, boolean plus, boolean fixed) {
        return  Schedule.builder()
                .member(member)
                .date(date)
                .todo(todo)
                .cost(cost)
                .plus(plus)
                .fixed(fixed)
                .build();
    }

    public static Schedule toPinnedSchedule(Member member, String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        return  Schedule.builder()
                .member(member)
                .todo(todo)
                .cost(cost)
                .plus(plus)
                .fixed(fixed)
                .periodType(periodType)
                .period(period)
                .build();
    }

    public void updateFutureSchedule(LocalDate date, String todo, int cost) {
        this.date = date;
        this.todo = todo;
        this.cost = cost;
    }
}
