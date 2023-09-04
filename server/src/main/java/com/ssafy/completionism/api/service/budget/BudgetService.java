package com.ssafy.completionism.api.service.budget;

import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {

    public List<MonthBudgetResponse> searchMonthAll(String phone);

    public List<MonthBudgetResponse> searchMonth(String phone, LocalDate startMonth, LocalDate endMonth);
}
