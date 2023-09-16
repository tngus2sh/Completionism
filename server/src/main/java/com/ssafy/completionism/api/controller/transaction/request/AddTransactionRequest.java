package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.api.service.transaction.dto.AddTransactionDto;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddTransactionRequest {

    @NotNull
    private Integer cost;
    @NotNull
    private Boolean plus;
    @NotNull
    private Category category;
    @NotBlank
    private String place;

    @Builder
    private AddTransactionRequest(Integer cost, Boolean plus, Category category, String place) {
        this.cost = cost;
        this.plus = plus;
        this.category = category;
        this.place = place;
    }

    public AddTransactionDto of() {
        return AddTransactionDto.builder()
                .cost(this.cost)
                .plus(this.plus)
                .category(this.category)
                .place(this.place)
                .build();
    }
}
