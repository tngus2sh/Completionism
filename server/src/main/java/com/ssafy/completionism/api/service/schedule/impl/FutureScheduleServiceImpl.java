package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.controller.schedule.request.CreateFutureScheduleRequest;
import com.ssafy.completionism.api.exception.NotFoundException;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.api.service.schedule.FutureScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FutureScheduleServiceImpl implements FutureScheduleService {

//    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Long createFutureSchedule(CreateFutureScheduleRequest request) {
//        Optional<Member> findMember = memberRepository.findByIdAndActive(request.getLoginId(), ACTIVE);
//
//        if(!findMember.isEmpty()) {
//            throw new NotFoundException("204", "해당 회원은 존재하지 않습니다.");
//        }
//        else {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.parse(request.getDate(), formatter);

        Schedule schedule = Schedule.builder()
//                .member(findMember.get())
                .date(date)
                .todo(request.getTodo())
                .cost(request.getCost())
                .plus(request.isPlus())
                .fixed(request.isFixed())
                .periodType(request.isPeriodType())
                .period(request.getPeriod())
                .build();

        Schedule futureSchedule = scheduleRepository.save(schedule);
        return futureSchedule.getId();
//        }
    }
}
