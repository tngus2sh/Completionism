package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryInPersonRequest;
import com.ssafy.completionism.domain.transaction.Feel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AddDiaryInPersonDto {

    private LocalDateTime time;
    private String diary;

    private Feel feel;


    @Builder
    public AddDiaryInPersonDto(LocalDateTime time, String diary, Feel feel) {
        this.time = time;
        this.diary = diary;
        this.feel = feel;
    }



    public static AddDiaryInPersonDto toDto(AddDiaryInPersonRequest request) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(request.getTime(), dateTimeFormatter);

        return AddDiaryInPersonDto.builder()
                .time(time)
                .diary(request.getDiary())
                .feel(Feel.valueOf(request.getFeel()))
                .build();
    }
}
