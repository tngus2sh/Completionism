package com.ssafy.completionism.api.controller.schedule.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class CreatePinnedScheduleRequest {

    @NotBlank
    @Size(max = 300)
    private String todo;

    @Range(min = 1)
    private int cost;

    @NotNull
    private boolean plus;

    @NotNull
    private boolean fixed;

    @NotNull
    private boolean periodType;

    @Range(min = 1, max = 31)
    private int period;


    @Builder
    private CreatePinnedScheduleRequest(String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
        this.periodType = periodType;
        this.period = period;
    }
}
