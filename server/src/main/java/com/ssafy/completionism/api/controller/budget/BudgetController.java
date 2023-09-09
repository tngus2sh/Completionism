package com.ssafy.completionism.api.controller.budget;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ApiResponse<List<MonthBudgetResponse>> getMonthAll() {
        // 사용자 정보 가져오기
        String phone = null;

        List<MonthBudgetResponse> list = budgetService.searchMonthAll(phone);
        return ApiResponse.ok(list);
    }

    @GetMapping("/{period}")
    public ApiResponse<List<MonthBudgetResponse>> getMonth(
            @PathVariable String period
    ) {
        // 사용자 정보 가져오기
        String phone = null;

        String[] dates = period.split("_");

        String[] date = dates[0].split("-");
        LocalDate start = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        date = dates[1].split("-");
        LocalDate end = LocalDate.from(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).atStartOfDay());

        List<MonthBudgetResponse> list = budgetService.searchMonth(phone, start, end);
        return ApiResponse.ok(list);
    }

}
