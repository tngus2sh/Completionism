package com.ssafy.completionism.api.controller.diary;

import com.ssafy.completionism.api.controller.diary.request.AddDiaryRequest;
import com.ssafy.completionism.api.controller.diary.response.DiaryResponse;
import com.ssafy.completionism.api.service.diary.DiaryService;
import com.ssafy.completionism.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    public void addDiary(
            @Valid @RequestBody AddDiaryRequest request
    ) {
        // TODO: 2023/09/10  다이어리 추가 서비스
    }


    @GetMapping
    public List<DiaryResponse> showDiarys() {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        // TODO: 2023/09/10 전체 다이어리 보여주기

        return null;
    }

    @GetMapping("/{diary_id}")
    public DiaryResponse showDiary(
            @PathVariable(name = "diary_id") Long diaryId
    ) {
        // 사용자 정보 가져오기
        String loginId = SecurityUtils.getCurrentLoginId();

        // TODO: 2023/09/10 세부 다이어리 보여주기

        return null;
    }

}