package com.ssafy.completionism.api.controller.diary;

import com.ssafy.completionism.api.controller.diary.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.diary.response.CompletionChatResponse;
import com.ssafy.completionism.api.service.diary.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class GptApiController {

    private final GptService gptService;

    @PostMapping("/gpt")
    public CompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest request) {
        log.info("request={}", request);
        return gptService.completionChat(request);
    }
}
