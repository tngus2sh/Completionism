package com.ssafy.completionism.domain.schedule.repository;

import com.ssafy.completionism.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
