package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.service.schedule.PinnedScheduleService;
import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberQueryRepository;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleQueryRepository;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class PinnedScheduleServiceImpl implements PinnedScheduleService {

    private final MemberQueryRepository memberQueryRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;

    @Override
    public Long createPinnedSchedule(String loginId, CreatePinnedScheduleDto dto) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        Schedule schedule = Schedule.toPinnedSchedule(member, dto.getTodo(), dto.getCost(), dto.isPlus(), dto.isFixed(), dto.isPeriodType(), dto.getPeriod());
        Schedule pinnedSchedule = scheduleRepository.save(schedule);

        return pinnedSchedule.getId();
    }
}
