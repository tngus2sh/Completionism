package com.ssafy.completionism.client.schedule;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    Long id;

    @Column(nullable = false)
    private LocalDateTime date;

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


    public Schedule() {}

    @Builder
    public Schedule(Long id, LocalDateTime date, String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
        this.periodType = periodType;
        this.period = period;
    }
}
