package com.ssafy.completionism.client.schedule.repository;

import com.ssafy.completionism.client.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
