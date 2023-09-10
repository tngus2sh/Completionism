package com.ssafy.completionism.api.service.transaction;

import com.ssafy.completionism.domain.transaction.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class HistoryService {

    private final HistoryRepository historyRepository;

}
