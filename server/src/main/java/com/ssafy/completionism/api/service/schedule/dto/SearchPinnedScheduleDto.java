package com.ssafy.completionism.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchPinnedScheduleDto {

    private Long id;
    private String todo;
    private int cost;
    private boolean plus;
    private boolean periodType;
    private int period;


    @Builder
    public SearchPinnedScheduleDto(Long id, String todo, int cost, boolean plus, boolean periodType, int period) {
        this.id = id;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.periodType = periodType;
        this.period = period;
    }
}
