package com.ssafy.completionism.api.service.diary.impl;

import com.ssafy.completionism.api.controller.diary.request.GPTCompletionChatRequest;
import com.ssafy.completionism.api.controller.diary.response.CompletionChatResponse;
import com.ssafy.completionism.api.service.diary.GptService;
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
    public CompletionChatResponse completionChat(GPTCompletionChatRequest request) {
        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
                GPTCompletionChatRequest.of(request));

        CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);

        List<String> messages = response.getMessages().stream()
                .map(CompletionChatResponse.Message::getMessage)
                .collect(Collectors.toList());

        log.info("messages={}", messages);
        return response;
    }

}
