package com.ssafy.completionism.api.service.transaction;

import com.ssafy.completionism.api.controller.transaction.response.TransactionResponse;
import com.ssafy.completionism.api.service.transaction.dto.AddTransactionDto;
import com.ssafy.completionism.api.service.transaction.dto.WriteDiaryDto;
import com.ssafy.completionism.domain.member.Member;
import com.ssafy.completionism.domain.member.repository.MemberRepository;
import com.ssafy.completionism.domain.transaction.History;
import com.ssafy.completionism.domain.transaction.Transaction;
import com.ssafy.completionism.domain.transaction.repository.HistoryQueryRepository;
import com.ssafy.completionism.domain.transaction.repository.HistoryRepository;
import com.ssafy.completionism.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final HistoryRepository historyRepository;
    private final HistoryQueryRepository historyQueryRepository;
    private final MemberRepository memberRepository;

    public TransactionResponse writeDiary(String loginId, Long transactionId, WriteDiaryDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(NoSuchElementException::new);

        if (!transaction.getHistory().getMember().getId().equals(member.getId())) {
            return null;
        }

        transaction.writeOneLineDiary(dto.getContent(), dto.getFeel());

        return createResponse(transaction);
    }

    public Long addTransaction(AddTransactionDto dto, String loginId, LocalDateTime transactionTime) {
        log.debug("[거래내역 등록((서비스))]");
        History todayHistory = null;

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        LocalDate transactionDate = transactionTime.toLocalDate();
        Optional<History> registeredHistory = historyQueryRepository.getRegisteredHistory(loginId, transactionDate.atStartOfDay());

        // 거래 내역이 생성되어 있지 않을 때
        if (registeredHistory.isEmpty()) {
            log.debug("[거래내역 등록((서비스))] 거래내역 없음");
            todayHistory = createHistoryEntity(member);
            log.debug("[거래내역 등록((서비스))] 거래내역 만듦 = {}", todayHistory.getId());
        }

        // 거래 내역이 생성되어 있을 때
        if (registeredHistory.isPresent()) {
            todayHistory = registeredHistory.get();
        }

        Transaction transaction = saveTransaction(dto, transactionTime, todayHistory);
        todayHistory.updateHistory(dto.getCost(), dto.isPlus());

        transaction.regist(todayHistory);
        return transaction.getId();
    }

    private Transaction saveTransaction(AddTransactionDto dto, LocalDateTime transactionTime, History todayHistory) {
        Transaction transaction = Transaction.builder()
                .time(transactionTime)
                .cost(dto.getCost())
                .plus(dto.isPlus())
                .category(dto.getCategory())
                .place(dto.getPlace())
                .diary("")
                .history(todayHistory)
                .build();
        return transactionRepository.save(transaction);
    }

    private History createHistoryEntity(Member member) {
        History history = History.builder()
                .income(0)
                .outcome(0)
                .diary("")
                .member(member)
                .transactions(new ArrayList<>())
                .build();
        return historyRepository.save(history);
    }

    private TransactionResponse createResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .time(transaction.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .cost(transaction.getCost())
                .isPlus(transaction.isPlus())
                .category(transaction.getCategory().getText())
                .place(transaction.getPlace())
                .diary(transaction.getDiary())
                .feel(transaction.getFeel().getText())
                .build();
    }
}
