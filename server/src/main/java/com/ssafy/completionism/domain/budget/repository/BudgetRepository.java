package com.ssafy.completionism.domain.budget.repository;

import com.ssafy.completionism.domain.Category;
import com.ssafy.completionism.domain.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
