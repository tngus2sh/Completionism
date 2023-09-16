package com.ssafy.completionism.api.service.transaction.dto;

import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequest;
import com.ssafy.completionism.api.controller.transaction.request.AddDiaryRequestList;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddDiaryDtoList {

    private  List<AddDiaryDto> diaries;

    @Builder
    public AddDiaryDtoList(List<AddDiaryDto> diaries) {
        this.diaries = diaries;
    }

    public static AddDiaryDtoList toDto(AddDiaryRequestList requestList) {
        List<AddDiaryDto> dtos = new ArrayList<>();

        for (AddDiaryRequest request : requestList.getDiaries()) {
            dtos.add(AddDiaryDto.toDto(request));
        }

        return AddDiaryDtoList.builder()
                .diaries(dtos)
                .build();
    }

}
