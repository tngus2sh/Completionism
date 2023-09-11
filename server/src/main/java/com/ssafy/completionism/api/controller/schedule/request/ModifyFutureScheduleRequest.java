package com.ssafy.completionism.api.controller.schedule.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ModifyFutureScheduleRequest {

    @Range(min = 1)
    private Long id;

    @NotBlank
    private String date;

    @NotBlank
    @Size(max = 300)
    private String todo;

    @Range(min = 1)
    private int cost;


    @Builder
    private ModifyFutureScheduleRequest(Long id, String date, String todo, int cost) {
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
    }
}
