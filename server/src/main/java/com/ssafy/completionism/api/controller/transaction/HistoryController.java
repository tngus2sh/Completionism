package com.ssafy.completionism.api.controller.transaction;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.transaction.response.HistoryListResponse;
import com.ssafy.completionism.api.service.transaction.HistoryService;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/history")
@Slf4j
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{loginId}/{period}")
    public ApiResponse<HistoryListResponse> getHistoryListForPeriod(@PathVariable String loginId, @PathVariable String period) {


        HistoryPeriodSearchCond cond = getSearchCond(period);

        HistoryListResponse response = historyService.getHistoryListUsingPeriod(loginId, cond);

        return ApiResponse.ok(response);
    }

    private static HistoryPeriodSearchCond getSearchCond(String period) {
        String[] days = period.split("_");
        String[] start = days[0].split("_");
        String[] end = days[1].split("-");
        LocalDate startDay = LocalDate.of(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2]));
        LocalDate endDay = LocalDate.of(Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]));
        return HistoryPeriodSearchCond.builder()
                .startDay(startDay)
                .endDay(endDay)
                .build();
    }
}
