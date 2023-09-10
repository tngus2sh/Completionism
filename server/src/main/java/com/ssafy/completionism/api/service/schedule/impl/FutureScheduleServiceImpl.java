package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.controller.schedule.request.ModifyFutureScheduleRequest;
import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberQueryRepository;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.api.service.schedule.FutureScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FutureScheduleServiceImpl implements FutureScheduleService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Long createFutureSchedule(String loginId, CreateFutureScheduleDto dto) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true).orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dto.getDate(), formatter);

        Schedule schedule = Schedule.toFutureSchedule(member, date, dto.getTodo(), dto.getCost(), dto.isPlus(), dto.isFixed());
        Schedule futureSchedule = scheduleRepository.save(schedule);

        return futureSchedule.getId();
    }

    @Override
    public void modifyFutureSchedule(ModifyFutureScheduleRequest request) {
//        Optional<Member> findMember = memberRepository.findByIdAndActive(request.getLoginId(), ACTIVE);
//
//        if(findMember.isEmpty()) {
//            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다.");
//        }

        Optional<Schedule> findSchedule = scheduleRepository.findById(request.getId());

        if(findSchedule.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 소비 일정이 존재하지 않습니다.");
        }
//        else if(!findSchedule.get().getMember().getLoginId().equals(request.getLoginId())) {
//            throw new NoAuthorizationException("401", HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
//        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDateTime.parse(request.getDate(), formatter);

        scheduleRepository.updateFutureSchedule(request.getId(), date, request.getTodo(), request.getCost());
    }

    @Override
    public void removeFutureSchedule(Long id) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);

        if(findSchedule.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 소비 일정이 존재하지 않습니다.");
        }
//        else if(!findSchedule.get().getMember().getLoginId().equals(request.getLoginId())) {
//            throw new NoAuthorizationException("401", HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
//        }

        scheduleRepository.delete(findSchedule.get());
    }
}
