package com.mrityunjay.dietease.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory; // <--- The correct M5 import!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiMemoryConfig {

    @Bean
    public ChatMemory chatMemory() {
        // In Spring AI 1.0.0-M5, this is the official class for session memory
        return new InMemoryChatMemory();
    }
}