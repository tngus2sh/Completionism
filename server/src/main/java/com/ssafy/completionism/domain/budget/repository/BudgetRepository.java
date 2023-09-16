package com.ssafy.completionism.domain.budget.repository;

import com.ssafy.completionism.domain.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
