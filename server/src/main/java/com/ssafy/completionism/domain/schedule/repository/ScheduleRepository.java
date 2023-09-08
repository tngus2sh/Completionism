package com.ssafy.completionism.domain.schedule.repository;

import com.ssafy.completionism.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Schedule s set s.date=:date, s.todo=:todo, s.cost=:cost where s.id=:id")
    Optional<Void> updateFutureSchedule(@Param("id") Long id, @Param("date")LocalDateTime date, @Param("todo") String todo, @Param("cost") int cost);
}
