package com.ssafy.completionism.api.service.budget.dto;

import com.ssafy.completionism.api.controller.budget.request.AddBudgetRequest;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yearMonth = LocalDate.parse(request.getYearMonth(), dateTimeFormatter).withDayOfMonth(1);
        return AddBudgetDto.builder()
                .yearMonth(yearMonth)
                .totalBudget(request.getTotalBudget())
                .category(Category.valueOf(request.getCategory()))
                .build();
    }

}
