package com.ssafy.completionism.api.controller.schedule.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateFutureScheduleRequest {

    @NotBlank
    private String date;

    @NotBlank
    @Size(max = 300)
    private String todo;

    @Range(min = 1)
    private int cost;

    @NotNull
    private boolean plus;

    @NotNull
    private boolean fixed;


    @Builder
    public CreateFutureScheduleRequest(String date, String todo, int cost, boolean plus, boolean fixed) {
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
    }
}
