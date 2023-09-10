package com.ssafy.completionism.domain.budget;

import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.TimeBaseEntity;
import com.ssafy.completionism.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
public class Budget extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "year_month", columnDefinition = "DATE")
    private LocalDate yearMonth;

    @Column(name = "total_budget")
    private int totalBudget;

    @Column
    private Category category;

    @Builder
    public Budget(Long id, Member member, LocalDate yearMonth, int totalBudget, Category category) {
        this.id = id;
        this.member = member;
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }

    public static Budget toBudget(Member member, LocalDate yearMonth, int totalBudget, Category category) {
        return Budget.builder()
                .member(member)
                .yearMonth(yearMonth)
                .totalBudget(totalBudget)
                .category(category)
                .build();
    }

    public void updateBudget(int totalBudget, Category category) {
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
