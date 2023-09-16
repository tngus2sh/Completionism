package com.ssafy.completionism.domain.transaction.repository;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionPeriodSearchCond {
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    @Builder
    public TransactionPeriodSearchCond(LocalDate startDay, LocalDate endDay) {
        this.startDay = startDay.atStartOfDay();
        this.endDay = endDay.plusDays(1).atStartOfDay();
    }

}
