package com.ssafy.completionism.api.controller.gpt;

import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;
import com.ssafy.completionism.api.service.gpt.GptService;
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
    public GPTCompletionChatResponse completionChat(final @RequestBody GPTCompletionChatRequest request) {
        log.info("request={}", request);
        return gptService.completionChat(request);
    }
}
