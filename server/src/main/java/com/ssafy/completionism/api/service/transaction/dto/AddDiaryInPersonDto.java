package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryInPersonRequest;
import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class AddDiaryInPersonDto {

    private LocalDate time;
    private String diary;

    private Feel feel;


    @Builder
    public AddDiaryInPersonDto(LocalDate time, String diary, Feel feel) {
        this.time = time;
        this.diary = diary;
        this.feel = feel;
    }

    public static AddDiaryInPersonDto toDto(AddDiaryInPersonRequest request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(request.getTime(), dateTimeFormatter);

        return AddDiaryInPersonDto.builder()
                .time(time)
                .diary(request.getDiary())
                .feel(Feel.valueOf(request.getFeel()))
                .build();
    }
}
