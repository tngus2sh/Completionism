package com.ssafy.completionism.api.service.schedule.dto;

import com.ssafy.completionism.api.controller.schedule.request.CreatePinnedScheduleRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class CreatePinnedScheduleDto {

    private String todo;
    private int cost;
    private boolean plus;
    private boolean fixed;
    private boolean periodType;
    private int period;


    @Builder
    private CreatePinnedScheduleDto(String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
        this.periodType = periodType;
        this.period = period;
    }

    public static CreatePinnedScheduleDto toDto(CreatePinnedScheduleRequest request) {
        return CreatePinnedScheduleDto.builder()
                .todo(request.getTodo())
                .cost(request.getCost())
                .plus(request.isPlus())
                .fixed(request.isFixed())
                .periodType(request.isPeriodType())
                .period(request.getPeriod())
                .build();
    }
}
