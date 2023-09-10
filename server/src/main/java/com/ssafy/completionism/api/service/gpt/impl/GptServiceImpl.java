package com.ssafy.completionism.api.service.gpt.impl;

import com.ssafy.completionism.api.controller.gpt.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.gpt.response.GPTCompletionChatResponse;
import com.ssafy.completionism.api.service.gpt.GptService;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptServiceImpl implements GptService {

    private final OpenAiService openAiService;

    @Override
    public GPTCompletionChatResponse completionChat(GPTCompletionChatRequest request) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                GPTCompletionChatRequest.of(request));

        GPTCompletionChatResponse response = GPTCompletionChatResponse.of(chatCompletion);

        List<String> messages = response.getMessages().stream()
                .map(GPTCompletionChatResponse.Message::getMessage)
                .collect(Collectors.toList());

        log.info("messages={}", messages);
        return response;
    }

}
