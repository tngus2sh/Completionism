package com.ssafy.completionism.api.service.transaction;

import com.ssafy.completionism.api.controller.transaction.response.DiaryResponse;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDtoList;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryInPersonDto;

import java.util.List;

public interface DiaryService {

    // 일기(다이어리) 등록
    public DiaryResponse addDiary(String loginId, AddDiaryDtoList dto);

    public void addDiaryInPersion(String loginId, AddDiaryInPersonDto dto);

    // 일기 전체 조회
    public List<DiaryResponse> showDiarys(String loginId);

    // 일기 상세 조회
    public DiaryResponse showDiary(String loginId, String diaryDate);
}
