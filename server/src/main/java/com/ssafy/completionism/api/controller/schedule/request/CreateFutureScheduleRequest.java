package com.ssafy.completionism.api.controller.schedule.request;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "shinhan")
    private String loginId;

    @NotBlank
    @ApiModelProperty(example = "2023-09-09")
    private String date;

    @NotBlank
    @Size(max = 300)
    @ApiModelProperty(example = "동기랑 목포 여행")
    private String todo;

    @Range(min = 1)
    @ApiModelProperty(example = "100")
    private int cost;

    @NotNull
    @ApiModelProperty(example = "true")
    private boolean plus;

    @NotNull
    @ApiModelProperty(example = "true")
    private boolean fixed;

    @NotNull
    @ApiModelProperty(example = "true")
    private boolean periodType;

    @Range(min = 1, max = 31)
    @ApiModelProperty(example = "2")
    private int period;


    @Builder
    public CreateFutureScheduleRequest(String loginId, String date, String todo, int cost, boolean plus, boolean fixed, boolean periodType, int period) {
        this.loginId = loginId;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
        this.plus = plus;
        this.fixed = fixed;
        this.periodType = periodType;
    }
}
