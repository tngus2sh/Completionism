package com.ssafy.completionism.api.controller.schedule.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FutureScheduleResponse {

    private Long id;
    private String date;
    private String todo;
    private int cost;
    private boolean plus;


    @Builder
    public FutureScheduleResponse(Long id, String date, String todo, int cost, boolean plus) {
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
    }
}
