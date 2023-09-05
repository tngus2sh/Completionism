package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AddTransactionDto {

    private LocalDateTime time;
    private int cost;
    private boolean plus;
    private Category category;
    private String place;

    @Builder
    private AddTransactionDto(LocalDateTime time, int cost, boolean plus, Category category, String place) {
        this.time = time;
        this.cost = cost;
        this.plus = plus;
        this.category = category;
        this.place = place;
    }
}
