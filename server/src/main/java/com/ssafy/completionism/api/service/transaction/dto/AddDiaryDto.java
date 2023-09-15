package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequest;
import com.ssafy.completionism.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime createdDate = LocalDateTime.parse(request.getCreatedDate(), dateTimeFormatter);

        return AddDiaryDto.builder()
                .category(Category.valueOf(request.getCategory()))
                .cost(request.getCost())
                .desc(request.getDesc())
                .createdDate(createdDate)
                .build();
    }

}
