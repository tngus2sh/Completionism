package com.ssafy.completionism.api.controller.schedule.response;

import com.ssafy.completionism.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

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

    public static FutureScheduleResponse toResponse(Schedule schedule) {
        String date = schedule.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return FutureScheduleResponse.builder()
                .id(schedule.getId())
                .date(date)
                .todo(schedule.getTodo())
                .cost(schedule.getCost())
                .plus(schedule.isPlus())
                .build();
    }
}
