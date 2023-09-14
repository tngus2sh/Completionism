package com.ssafy.completionism.api.controller.schedule.response;

import com.ssafy.completionism.api.service.schedule.dto.SearchPinnedScheduleDto;
import com.ssafy.completionism.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ScheduleResponse {

    private Long id;
    private String date;
    private String todo;
    private int cost;
    private boolean plus;


    @Builder
    public ScheduleResponse(Long id, String date, String todo, int cost, boolean plus) {
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
    }

    public static ScheduleResponse toResponse(Schedule schedule) {
        String date = schedule.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return ScheduleResponse.builder()
                .id(schedule.getId())
                .date(date)
                .todo(schedule.getTodo())
                .cost(schedule.getCost())
                .plus(schedule.isPlus())
                .build();
    }

    public static ScheduleResponse toResponse(String date, SearchPinnedScheduleDto dto) {
        return ScheduleResponse.builder()
                .id(dto.getId())
                .date(date)
                .todo(dto.getTodo())
                .cost(dto.getCost())
                .plus(dto.isPlus())
                .build();
    }
}
