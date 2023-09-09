package com.ssafy.completionism.api.controller.schedule.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FutureScheduleResponse {

    private Long id;
    private String memberId;
    private String date;
    private String todo;
    private int cost;
    private boolean plus;
    private boolean fixed;


    @Builder
    public FutureScheduleResponse(Long id, String memberId, String date, String todo, int cost, boolean plus, boolean fixed) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
    }
}
