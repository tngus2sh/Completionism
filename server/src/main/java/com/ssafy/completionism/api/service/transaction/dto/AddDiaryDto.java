package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequest;
import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AddDiaryDto {

    private LocalDateTime time;

    private int cost;

    private Category category;

    private String place;

    private String diary;

    private Feel feel;

    private boolean plus;

    @Builder
    public AddDiaryDto(LocalDateTime time, int cost, Category category, String place, String diary, Feel feel, boolean plus) {
        this.time = time;
        this.cost = cost;
        this.category = category;
        this.place = place;
        this.diary = diary;
        this.feel = feel;
        this.plus = plus;
    }


    public static AddDiaryDto toDto(AddDiaryRequest request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(request.getTime(), dateTimeFormatter);

        return AddDiaryDto.builder()
                .time(time)
                .cost(request.getCost())
                .category(Category.valueOf(request.getCategory()))
                .place(request.getPlace())
                .diary(request.getDiary())
                .feel(Feel.valueOf(request.getFeel()))
                .plus(request.isPlus())
                .build();
    }
}
