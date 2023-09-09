package com.ssafy.completionism.api.service.diary;

import com.ssafy.completionism.api.service.diary.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.diary.dto.DiaryDto;

import java.util.List;

public interface DiaryService {

    // 일기(다이어리) 등록
    public Long addDiary(AddDiaryDto dto);

    // 일기 전체 조회
    public List<DiaryDto> showDiarys(String loginId);

    // 일기 상세 조회
    public DiaryDto showDiary(String loginId, Long diary_id);
}
