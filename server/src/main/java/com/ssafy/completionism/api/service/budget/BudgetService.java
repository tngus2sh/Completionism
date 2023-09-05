package com.ssafy.completionism.api.service.budget;

import com.ssafy.completionism.api.controller.budget.response.MonthBudgetResponse;
import com.ssafy.completionism.api.service.budget.dto.AddBudgetDto;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {

    public Long addBudget(String phone, AddBudgetDto dto);

    public List<MonthBudgetResponse> searchMonthAll(String phone);

    public List<MonthBudgetResponse> searchMonth(String phone, LocalDate startMonth, LocalDate endMonth);
}
