package com.ssafy.completionism.api.service.schedule.impl;

import com.ssafy.completionism.api.controller.schedule.response.ScheduleResponse;
import com.ssafy.completionism.api.service.schedule.PinnedScheduleService;
import com.ssafy.completionism.api.service.schedule.ScheduleService;
import com.ssafy.completionism.api.service.schedule.dto.CreatePinnedScheduleDto;
import com.ssafy.completionism.api.service.schedule.dto.SearchPinnedScheduleDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberQueryRepository;
import com.ssafy.completionism.domain.schedule.Schedule;
import com.ssafy.completionism.domain.schedule.repository.ScheduleQueryRepository;
import com.ssafy.completionism.domain.schedule.repository.ScheduleRepository;
import com.ssafy.completionism.global.exception.NoAuthorizationException;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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

    @Override
    public List<ScheduleResponse> searchPinnedScheduleAll(String loginId) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        List<SearchPinnedScheduleDto> dtoList = scheduleQueryRepository.getPinnedSchedules(loginId);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-");
        String yearMonth = now.format(formatter);

        List<ScheduleResponse> response = new ArrayList<>();
        for(SearchPinnedScheduleDto dto: dtoList) {
            if(dto.isPeriodType()) {
                String date = dto.getPeriod() < 10? yearMonth + "0" + dto.getPeriod(): yearMonth + dto.getPeriod();

                ScheduleResponse scheduleResponse = ScheduleResponse.toResponse(date, dto);
                response.add(scheduleResponse);
            }
            else {
                int startDay = findStartDay(now, dto.getPeriod());
                int endDay = now.withDayOfMonth(now.lengthOfMonth()).getDayOfMonth();

                System.out.println("start: " + startDay +", end: " + endDay);

                for(int i = startDay; i <= endDay; i += 7) {
                    String date = i < 10? yearMonth + "0" + i: yearMonth + i;
                    ScheduleResponse scheduleResponse = ScheduleResponse.toResponse(date, dto);
                    response.add(scheduleResponse);
                }
            }
        }

        return response;
    }

    @Override
    public Integer countDailyPinnedSchedule(String loginId, String date) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        List<SearchPinnedScheduleDto> dtoList = scheduleQueryRepository.getPinnedSchedules(loginId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(date, formatter);

        int day = Integer.parseInt(date.substring(8));

        int total = 0;
        for(SearchPinnedScheduleDto dto: dtoList) {
            System.out.println("periodType: " + dto.isPeriodType() + ", period: " + dto.getPeriod());

            if (dto.isPeriodType()) {
                if (dto.getPeriod() == day) {
                    total -= dto.getCost();
                }
            } else {
                int startDay = findStartDay(targetDate, dto.getPeriod());
                if ((day - startDay) % 7 == 0) {
                    total -= dto.getCost();
                }
            }
        }

        return total;
    }

    @Override
    public Integer countNextPinnedSchedule(String loginId, String date) {
        Member member = memberQueryRepository.getByLoginIdAndActive(loginId, true)
                .orElseThrow(() -> new NotFoundException("404", HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        List<SearchPinnedScheduleDto> dtoList = scheduleQueryRepository.getPinnedSchedules(loginId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(date, formatter);

        String yearMonth = date.substring(0, 8);
        int day = Integer.parseInt(date.substring(8));

        List<ScheduleResponse> response = new ArrayList<>();
        for(SearchPinnedScheduleDto dto: dtoList) {
            if(dto.isPeriodType()) {
                if(day < dto.getPeriod()) {
                    String target = dto.getPeriod() < 10? yearMonth + "0" + dto.getPeriod(): yearMonth + dto.getPeriod();
                    ScheduleResponse scheduleResponse = ScheduleResponse.toResponse(target, dto);
                    response.add(scheduleResponse);
                }
            }
            else {
                int startDay = findNextStartDay(targetDate, dto.getPeriod());
                int endDay = targetDate.withDayOfMonth(targetDate.lengthOfMonth()).getDayOfMonth();

                System.out.println("start: " + startDay +", end: " + endDay);

                for(int i = startDay; i <= endDay; i += 7) {
                    String target = i < 10? yearMonth + "0" + i: yearMonth + i;
                    ScheduleResponse scheduleResponse = ScheduleResponse.toResponse(target, dto);
                    response.add(scheduleResponse);
                }
            }
        }

        int total = 0;
        for(ScheduleResponse res: response) {
            total -= res.getCost();
        }

        return total;
    }

    private int findStartDay(LocalDate now, int week) {
        int year = now.getYear();
        int month = now.getMonthValue();

        for(int i = 1; i <= 7; i++) {
            LocalDate date = LocalDate.of(year, month, i);
            int weekNumber = date.getDayOfWeek().getValue(); // 월요일이 1, 일요일이 7

            if(week == weekNumber) {
                return i;
            }
        }

        return -1;
    }

    private int findNextStartDay(LocalDate now, int week) {
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        for(int i = 1; i <= 31; i++) {
            if(i <= day) {
                continue;
            }
            LocalDate date = LocalDate.of(year, month, i);
            int weekNumber = date.getDayOfWeek().getValue(); // 월요일이 1, 일요일이 7

            if(week == weekNumber) {
                return i;
            }
        }

        return -1;
    }
}
