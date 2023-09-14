package com.ssafy.completionism.global.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class GptConfig {

    @Value("${gpt.apiKey}")
    private String token;

    @Bean
    public OpenAiService openAiService() {
        log.info("token={}", token);
        return new OpenAiService(token, Duration.ofSeconds(60));
    }
}
