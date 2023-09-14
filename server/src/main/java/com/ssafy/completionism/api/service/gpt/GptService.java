package com.ssafy.completionism.api.service.gpt;

import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;

public interface GptService {

    public GPTCompletionChatResponse completionChat(GPTCompletionChatRequest request);
}
