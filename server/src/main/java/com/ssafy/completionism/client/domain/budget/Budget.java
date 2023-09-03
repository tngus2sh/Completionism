package com.ssafy.completionism.client.domain.budget;

import com.ssafy.completionism.client.domain.Category;
import com.ssafy.completionism.client.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Budget extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

//    @Column(name = "member_id")
//    @ManyToOne
//    private Member member;

    @Column(name = "year_month")
    private Date yearMonth;

    @Column(name = "total_budget")
    private int totalBudget;

    @Column(name = "category")
    private Category category;

    @Builder
    public Budget(Long id, Date yearMonth, int totalBudget, Category category) {
        this.id = id;
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
