package com.ssafy.completionism.api.controller.transaction.request;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AddDiaryRequestList {

    private List<AddDiaryRequest> diaries;

    @Builder
    public AddDiaryRequestList(List<AddDiaryRequest> diaries) {
        this.diaries = diaries;
    }
}
