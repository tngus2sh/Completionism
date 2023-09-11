package com.ssafy.completionism.api.service.transaction;

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

    public String writeDiary(String loginId, Long transactionId, WriteDiaryDto dto) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(NoSuchElementException::new);

        if (!transaction.getHistory().getMember().getId().equals(member.getId())) {
            return null;
        }

        transaction.writeOneLineDiary(dto.getContent());

        return transaction.getDiary();
    }

    public Long addTransaction(AddTransactionDto dto, String loginId, LocalDateTime transactionTime) {
        log.debug("[거래내역 등록((서비스))]");
        History todayHistory = null;

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(NoSuchElementException::new);

        LocalDate transactionDate = transactionTime.toLocalDate();
        Optional<History> registeredHistory = historyQueryRepository.getRegisteredHistory(loginId, transactionDate.atStartOfDay());

        // 거래 내역이 생성되어 있을 때
        if (registeredHistory.isEmpty()) {
            todayHistory = createHistoryEntity(member);
        }

        // 거래 내역이 생성되어 있지 않을 때
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
                .build();
        return historyRepository.save(history);
    }
}
