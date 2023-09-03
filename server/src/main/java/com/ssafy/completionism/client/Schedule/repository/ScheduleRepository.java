package com.ssafy.completionism.client.Schedule.repository;

import com.ssafy.completionism.client.Schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
