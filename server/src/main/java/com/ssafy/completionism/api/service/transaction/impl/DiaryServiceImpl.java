package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;
import com.ssafy.completionism.api.controller.transaction.response.DiaryResponse;
import com.ssafy.completionism.api.service.gpt.GptService;
import com.ssafy.completionism.api.service.transaction.DiaryService;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.transaction.dto.DiaryDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.completionism.global.common.Constants.NOT_FOUND_DIARY;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final MemberRepository memberRepository;
    private final HistoryRepository historyRepository;
    private final HistoryQueryRepository historyQueryRepository;
    private final GptService gptService;

    /**
     * 다이어리 등록
     * @param dto 다이어리 등록 객체
     */
    @Override
    public void addDiary(String loginId, AddDiaryDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        String sendMessage = "나는 " + dto.getCategory().getText()+ "로 "
                + dto.getCost() + "를 썼고, "
                + dto.getDesc() + " 와 같은 마음이 들었어."
                + "이 정보들을 가지고 사실만을 바탕으로 일기를 써줘.";

        GPTCompletionChatResponse gptAnswer = gptService.completionChat(GPTCompletionChatRequest.builder()
                .role("user")
                .message(sendMessage)
                .build());

        String diary = gptAnswer.getMessages().get(0).getMessage();

        historyQueryRepository.getRegisteredHistory(loginId, dto.getCreatedDate()).ifPresent((history -> {
            history.updateDiary(diary);
        }));
    }

    /**
     * 해당 사용자의 다이어리 전체 조회
     * @param loginId 사용자 아이디
     * @return 다이어리 리스트
     */
    @Override
    public List<DiaryResponse> showDiarys(String loginId) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        return historyQueryRepository.getDiaryResponse(loginId);
    }

    /**
     * 해당 사용자의 해당 다이어리의 상세 조회
     * @param loginId 사용자 아이디
     * @param diaryDate 일기 쓴 날짜
     * @return 일기 상세 내용
     */
    @Override
    public DiaryResponse showDiary(String loginId, String diaryDate) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        LocalDateTime diaryDateTme = LocalDateTime.parse(diaryDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Optional<DiaryResponse> registeredDiaryOptional = historyQueryRepository.getRegisteredDiary(loginId, diaryDateTme);

        if (registeredDiaryOptional.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, NOT_FOUND_DIARY);
        }

        return registeredDiaryOptional.get();
    }
}
