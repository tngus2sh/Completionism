package com.ssafy.completionism.domain.transaction.repository;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HistoryPeriodSearchCond {

    private LocalDateTime startDay;
    private LocalDateTime endDay;

    @Builder
    public HistoryPeriodSearchCond(LocalDate startDay, LocalDate endDay) {
        this.startDay = startDay.atStartOfDay();
        this.endDay = endDay.plusDays(1).atStartOfDay();
    }
}
