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
public class ModifyFutureScheduleRequest {

    @NotBlank
    @ApiModelProperty(example = "shinhan")
    private String loginId;

    @Range(min = 1)
    @ApiModelProperty(example = "1")
    private Long id;

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


    @Builder
    public ModifyFutureScheduleRequest(String loginId, Long id, String date, String todo, int cost) {
        this.loginId = loginId;
        this.id = id;
        this.date = date;
        this.todo = todo;
        this.cost = cost;
    }
}
