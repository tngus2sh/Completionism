package com.ssafy.completionism.api.service.schedule.dto;

import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ModifyFutureScheduleDto {

    private Long id;
    private String date;
    private String todo;
    private int cost;


    @Builder
    private ModifyFutureScheduleDto(Long id, String date, String todo, int cost) {
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
    }

    public static ModifyFutureScheduleDto toDto(ModifyFutureScheduleRequest request) {
        return ModifyFutureScheduleDto.builder()
                .id(request.getId())
                .date(request.getDate())
                .todo(request.getTodo())
                .cost(request.getCost())
                .build();
    }
}
