package com.ssafy.completionism.api.controller.budget.request;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AddBudgetRequest {

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
    public AddBudgetRequest(LocalDate yearMonth, int totalBudget, Category category) {
        this.yearMonth = yearMonth;
        this.totalBudget = totalBudget;
        this.category = category;
    }
}
