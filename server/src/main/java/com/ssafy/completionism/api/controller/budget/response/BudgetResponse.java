package com.ssafy.completionism.api.controller.budget.response;

import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.budget.Budget;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
public class BudgetResponse {

    private Long id;

    private LocalDate yearMonth;

    private int totalBudget;

    private Category category;

    @Builder
    public BudgetResponse(Long id, LocalDate yearMonth, int totalBudget, Category category) {
        this.id = id;
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }


    public static BudgetResponse toResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .yearMonth(budget.getYearMonth())
                .totalBudget(budget.getTotalBudget())
                .category(budget.getCategory())
                .build();
    }

}
