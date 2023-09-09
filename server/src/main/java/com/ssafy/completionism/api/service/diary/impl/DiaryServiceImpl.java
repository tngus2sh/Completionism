package com.ssafy.completionism.api.service.diary.impl;

import com.ssafy.completionism.api.service.diary.DiaryService;
import com.ssafy.completionism.api.service.diary.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.diary.dto.DiaryDto;

import java.util.List;

public class DiaryServiceImpl implements DiaryService {

    /**
     * 다이어리 등록
     * @param dto 다이어리 등록 객체
     * @return 다이어리 식별키
     */
    @Override
    public Long addDiary(AddDiaryDto dto) {
        return null;
    }

    /**
     * 해당 사용자의 다이어리 전체 조회
     * @param loginId 사용자 아이디
     * @return 다이어리 리스트
     */
    @Override
    public List<DiaryDto> showDiarys(String loginId) {
        return null;
    }

    /**
     * 해당 사용자의 해당 다이어리의 상세 조회
     * @param loginId 사용자 아이디
     * @param diary_id 다이어리(일기) 식별키
     * @return 일기 상세 내용
     */
    @Override
    public DiaryDto showDiary(String loginId, Long diary_id) {
        return null;
    }
}
