package com.ssafy.completionism.api.service.diary;

import com.ssafy.completionism.api.controller.diary.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.diary.response.CompletionChatResponse;

public interface GptService {

    public CompletionChatResponse completionChat(GPTCompletionChatRequest request);
}
