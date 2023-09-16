package com.ssafy.completionism.api.service.transaction.impl;

import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;
import com.ssafy.completionism.api.controller.transaction.response.DiaryResponse;
import com.ssafy.completionism.api.service.gpt.GptService;
import com.ssafy.completionism.api.service.transaction.DiaryService;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDto;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryDtoList;
import com.ssafy.completionism.api.service.transaction.dto.AddDiaryInPersonDto;
import com.ssafy.completionism.api.service.transaction.dto.DiaryDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.History;
import com.ssafy.completionism.domain.transaction.repository.HistoryPeriodSearchCond;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryRepository;
import com.ssafy.completionism.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ssafy.completionism.global.common.Constants.NOT_FOUND_DIARY;
import static org.springframework.data.repository.util.ClassUtils.ifPresent;

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
    public DiaryResponse addDiary(String loginId, AddDiaryDtoList dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        List<AddDiaryDto> diaries = dto.getDiaries();

        StringBuilder send = new StringBuilder();

        for (AddDiaryDto diary : diaries) {
            String createdDate = diary.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String createdDay = diary.getTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

            String plusContent = null;
            if (diary.isPlus()) {
                plusContent = "를 썼고, ";
            } else {
                plusContent = "의 돈이 들어왔고,";
            }

            String sendMessage = "오늘은 " + createdDate + "이고"
                    + "오늘 요일은 " + createdDay + "야."
                    + "내가 있던 위치는 " + diary.getPlace() + " 이야\n"
                    + "나는 " + diary.getCategory().getText()+ "로 "
                    + diary.getCost() + plusContent + "\n"
                    + "내 기분은 " + diary.getFeel().getText() + " 라는 걸 참고해주고,"
                    + diary.getDiary() + " 와 같은 마음이 들었어. \n";

            send.append(sendMessage);
        }

        send.append("이 정보들을 바탕으로 일기를 만들어줘.");


        GPTCompletionChatResponse gptAnswer = gptService.completionChat(GPTCompletionChatRequest.builder()
                .role("user")
                .message(send.toString())
                .build());

        String diary = gptAnswer.getMessages().get(0).getMessage();

        // 기분 출력
        String feelSend = diary + "\n"
                + "여기까지 일기 내용인데 전체적인 일기 내용을 바탕으로 감정을 판별해주라. \n"
                + "감정은 '너무 좋음', '감사', '신남', '화남', '역겨움', '공포', '약간 슬픔', '호기심', '놀람', '그저 그런 감정', '행복', '슬픔' 이 중에서 하나 선택해서 말해줘";

        GPTCompletionChatResponse gptFeelAnswer = gptService.completionChat(GPTCompletionChatRequest.builder()
                .role("user")
                .message(feelSend)
                .build());

        Optional<History> registeredHistory = historyQueryRepository.getRegisteredHistory(loginId, dto.getDiaries().get(0).getTime());

        if (registeredHistory.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 거래 내역을 찾을 수가 없습니다.");
        }

        registeredHistory.get().updateDiary(diary);

        return DiaryResponse.builder()
                .diary(diary)
                .feel(gptFeelAnswer.getMessages().get(0).getMessage().split(":")[1].trim())
                .build();
    }

    @Override
    public void addDiaryInPersion(String loginId, AddDiaryInPersonDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Optional<History> registeredHistory = historyQueryRepository.getRegisteredHistory(loginId, dto.getTime().atStartOfDay());

        if (registeredHistory.isEmpty()) {
            throw new NotFoundException("404", HttpStatus.NOT_FOUND, "해당하는 거래 내역을 찾을 수가 없습니다.");
        }

        registeredHistory.get().updateDiary(dto.getDiary());
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
