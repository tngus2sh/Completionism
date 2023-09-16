package com.ssafy.completionism.api.controller.transaction;

import com.ssafy.completionism.api.ApiResponse;
import com.ssafy.completionism.api.controller.transaction.request.AddDiaryInPersonRequest;
import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequest;
import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequestList;
import com.ssafy.completionism.api.controller.transaction.response.DiaryResponse;
import com.ssafy.completionism.api.service.transaction.DiaryService;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDtoList;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryInPersonDto;
import com.ssafy.completionism.global.exception.NotFoundException;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

import static com.ssafy.completionism.global.common.Constants.NOT_FOUND_MEMBER;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    public ApiResponse<DiaryResponse> addDiary(
            @Valid @RequestBody AddDiaryRequestList request
    ) {
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            DiaryResponse diaryResponse = diaryService.addDiary(loginId, AddDiaryDtoList.toDto(request));
            return ApiResponse.ok(diaryResponse);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        } catch (NotFoundException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

    @PostMapping("/in-person")
    public ApiResponse<Void> addDiaryInPerson(
            @Valid @RequestBody AddDiaryInPersonRequest request
    ) {
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            diaryService.addDiaryInPersion(loginId, AddDiaryInPersonDto.toDto(request));
            return ApiResponse.ok(null);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        } catch (NotFoundException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }


    @GetMapping
    public ApiResponse<List<DiaryResponse>> showDiarys() {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        List<DiaryResponse> diaryList = diaryService.showDiarys(loginId);
        return ApiResponse.ok(diaryList);
    }

    @GetMapping("/{diary_date}")
    public ApiResponse<DiaryResponse> showDiary(
            @PathVariable(name = "diary_date") String diaryDate
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        try {
            DiaryResponse diary = diaryService.showDiary(loginId, diaryDate);
            return ApiResponse.ok(diary);
        } catch (NoSuchElementException e) {
            return ApiResponse.of(404, HttpStatus.NOT_FOUND, NOT_FOUND_MEMBER, null);
        } catch (NotFoundException e) {
            return ApiResponse.of(Integer.parseInt(e.getResultCode()), e.getHttpStatus(), e.getResultMessage(), null);
        }
    }

}