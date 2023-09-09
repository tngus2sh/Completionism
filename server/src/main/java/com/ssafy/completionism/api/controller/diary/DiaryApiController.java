package com.ssafy.completionism.api.controller.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryApiController {


    @GetMapping
    public void getDiarys() {
    }

    @GetMapping("/{diary_id}")
    public void getDiary(@PathVariable(name = "diary_id") Long diaryId) {

    }

}