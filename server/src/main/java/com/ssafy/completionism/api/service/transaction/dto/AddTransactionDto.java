package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTransactionDto {

    private int cost;
    private boolean plus;
    private Category category;
    private String place;

    @Builder
    private AddTransactionDto(int cost, boolean plus, Category category, String place) {
        this.cost = cost;
        this.plus = plus;
        this.category = category;
        this.place = place;
    }
}
