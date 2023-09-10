package com.ssafy.completionism.api.service.budget.dto;

import com.ssafy.completionism.api.controller.budget.request.AddBudgetRequest;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddBudgetDto {

    private LocalDate yearMonth;

    private int totalBudget;

    private Category category;

    @Builder
    public AddBudgetDto(LocalDate yearMonth, int totalBudget, Category category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }

    public static AddBudgetDto toDto(AddBudgetRequest request) {
        return AddBudgetDto.builder()
                .yearMonth(request.getYearMonth())
                .totalBudget(request.getTotalBudget())
                .category(request.getCategory())
                .build();
    }

}
