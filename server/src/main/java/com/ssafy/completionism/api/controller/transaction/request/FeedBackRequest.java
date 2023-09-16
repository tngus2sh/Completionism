package com.ssafy.completionism.api.controller.transaction.request;

import com.ssafy.completionism.domain.transaction.StatisticsType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class FeedBackRequest {

    @NotBlank
    @Pattern(regexp = "^(DAY|MONTH|YEAR)$")
    private StatisticsType type; // 월별, 일별, 연별
}
