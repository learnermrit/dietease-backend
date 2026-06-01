package com.mrityunjay.dietease.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietAiChatService {

    private final ChatClient chatClient;

    @Autowired
    @Qualifier("nutritionVectorStore")
    private VectorStore nutritionStore; // Physically isolated database!

    public DietAiChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String askNutritionist(String userQuestion) {
        
        // Searches ONLY the nutrition_vectors table
        List<Document> similarData = nutritionStore.similaritySearch(userQuestion);
        
        String systemKnowledge = similarData.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        String promptString = """
            You are the official DietEase AI Nutritionist. 
            Answer the user's question using ONLY the following verified database information:
            {knowledge}
            
            If the information does not contain the answer, politely say you don't have that data.
            
            User Question: {question}
            """;

        PromptTemplate template = new PromptTemplate(promptString);
        template.add("knowledge", systemKnowledge);
        template.add("question", userQuestion);

        return chatClient.prompt(template.create()).call().content();
    }
}