package com.ssafy.completionism.api.service.budget.dto;

import com.ssafy.completionism.api.controller.budget.request.ModifyBudgetRequest;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Data
public class ModifyBudgetDto {

    @NotBlank
    @FutureOrPresent
    private LocalDate yearMonth;

    @NotNull
    @Positive
    private int totalBudget;

    @NotBlank
    @Pattern(regexp = "^(ALL|TRAFFIC|FOOD|SHOPPING|LIFE|ETC)$")
    private Category category;

    @Builder
    public ModifyBudgetDto(LocalDate yearMonth, int totalBudget, Category category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }

    public static ModifyBudgetDto toDto(ModifyBudgetRequest request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yearMonth = LocalDate.from(LocalDate.parse(request.getYearMonth(), dateTimeFormatter).atStartOfDay());
        return ModifyBudgetDto.builder()
                .yearMonth(yearMonth)
                .totalBudget(request.getTotalBudget())
                .category(Category.valueOf(request.getCategory()))
                .build();
    }
}
