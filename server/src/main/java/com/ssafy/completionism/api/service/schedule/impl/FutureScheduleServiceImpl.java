package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.controller.schedule.response.ScheduleResponse;
import com.ssafy.completionism.api.service.schedule.dto.CreateFutureScheduleDto;
import com.ssafy.completionism.api.service.schedule.dto.ModifyFutureScheduleDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberQueryRepository;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.schedule.repository.ScheduleQueryRepository;
import com.ssafy.completionism.global.exception.NoAuthorizationException;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.api.service.schedule.FutureScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FutureScheduleServiceImpl implements FutureScheduleService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;

    @Override
    public Long createFutureSchedule(String loginId, CreateFutureScheduleDto dto) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dto.getDate(), formatter);

        Schedule schedule = Schedule.toFutureSchedule(member, date, dto.getTodo(), dto.getCost(), dto.isPlus(), dto.isFixed());
        Schedule futureSchedule = scheduleRepository.save(schedule);

        return futureSchedule.getId();
    }

    @Override
    public void modifyFutureSchedule(String loginId, ModifyFutureScheduleDto dto) {
        Schedule schedule = scheduleRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 소비 일정이 존재하지 않습니다."));

        Member member = schedule.getMember();

        if(!member.isActive()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다.");
        }
        else if(!member.getLoginId().equals(loginId)) {
            throw new NoAuthorizationException("401", HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dto.getDate(), formatter);

        schedule.updateFutureSchedule(date, dto.getTodo(), dto.getCost());
    }

    @Override
    public List<ScheduleResponse> searchFutureScheduleAll(String loginId) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        List<ScheduleResponse> response = scheduleQueryRepository.getFutureSchedules(loginId);

        return response;
    }

    @Override
    public ScheduleResponse searchFutureSchedule(String loginId, Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 소비 일정이 존재하지 않습니다."));

        Member member = schedule.getMember();

        if(!member.isActive()) {
            throw new NotFoundException("401", HttpStatus.UNAUTHORIZED, "탈퇴한 회원입니다.");
        }
        else if(!member.getLoginId().equals(loginId)) {
            throw new NoAuthorizationException("401", HttpStatus.UNAUTHORIZED, "조회 권한이 없습니다.");
        }

        return ScheduleResponse.toResponse(schedule);
    }

    @Override
    public Integer countDailyFutureSchedule(String loginId, String date) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate target = LocalDate.parse(date, formatter);

        int total = 0;
        Optional<Integer> expenseOptional = scheduleQueryRepository.countExpenseMonthlyFutureSchedule(loginId, target);
        if (expenseOptional.isPresent()) {
            total += expenseOptional.get();
        }

        Optional<Integer> incomeOptional = scheduleQueryRepository.countIncomeDailyFutureSchedule(loginId, target);
        if (incomeOptional.isPresent()) {
            total += incomeOptional.get();
        }

        return total;
    }
}
