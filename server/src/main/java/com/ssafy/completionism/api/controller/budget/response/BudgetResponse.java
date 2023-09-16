package com.ssafy.completionism.api.controller.budget.response;

import com.ssafy.completionism.domain.budget.Budget;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class BudgetResponse {

    private Long id;

    private String yearMonth;

    private int totalBudget;

    private String category;

    @Builder
    public BudgetResponse(Long id, String yearMonth, int totalBudget, String category) {
        this.id = id;
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }

    public static BudgetResponse toResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .yearMonth(budget.getYearMonth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .totalBudget(budget.getTotalBudget())
                .category(budget.getCategory().name())
                .build();
    }

}
