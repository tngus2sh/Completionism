package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.service.schedule.ScheduleService;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.global.exception.NoAuthorizationException;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public void removeSchedule(String loginId, Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 소비 일정이 존재하지 않습니다."));

        Member member = schedule.getMember();

        if(!member.isActive()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다.");
        }
        else if(!member.getLoginId().equals(loginId)) {
            throw new NoAuthorizationException("401", HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
