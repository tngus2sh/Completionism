package com.ssafy.completionism.domain.transaction.repository;

import com.ssafy.completionism.domain.transaction.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
