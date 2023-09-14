package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequest;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddDiaryDto {

    private Category category;

    private int cost;

    private String desc;

    private LocalDateTime createdDate;

    @Builder
    public AddDiaryDto(Category category, int cost, String desc, LocalDateTime createdDate) {
        this.category = category;
        this.cost = cost;
        this.desc = desc;
        this.createdDate = createdDate;
    }

    public static AddDiaryDto toDto(AddDiaryRequest request) {
        return AddDiaryDto.builder()
                .category(request.getCategory())
                .cost(request.getCost())
                .desc(request.getDesc())
                .createdDate(request.getCreatedDate())
                .build();
    }

}
