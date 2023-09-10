package com.ssafy.completionism.api.service.schedule.dto;

import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateFutureScheduleDto {

    private String date;
    private String todo;
    private int cost;
    private boolean plus;
    private boolean fixed;


    @Builder
    public CreateFutureScheduleDto(String date, String todo, int cost, boolean plus, boolean fixed) {
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
    }

    public static CreateFutureScheduleDto toDto(CreateFutureScheduleRequest request) {
        return CreateFutureScheduleDto.builder()
                .date(request.getDate())
                .todo(request.getTodo())
                .cost(request.getCost())
                .plus(request.isPlus())
                .fixed(request.isFixed())
                .build();
    }
}
