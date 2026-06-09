package com.mrityunjay.dietease.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
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

    @Autowired
    @Qualifier("devOpsVectorStore")
    private VectorStore devOpsStore;

    // public DietAiChatService(ChatClient.Builder chatClientBuilder) {
    //     this.chatClient = chatClientBuilder.build();
    // }
    public DietAiChatService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
            // We attach the Memory Advisor globally so the client knows how to remember things
            this.chatClient = chatClientBuilder
                    .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                    .build();
        }

    // public String askNutritionist(String userQuestion) {
        
    //     // Searches ONLY the nutrition_vectors table
    //     List<Document> similarData = nutritionStore.similaritySearch(userQuestion);
        
    //     String systemKnowledge = similarData.stream()
    //             .map(Document::getContent)
    //             .collect(Collectors.joining("\n"));

    //     String promptString = """
    //         You are the official DietEase AI Nutritionist. 
    //         Answer the user's question using ONLY the following verified database information:
    //         {knowledge}
            
    //         If the information does not contain the answer, politely say you don't have that data.
            
    //         User Question: {question}
    //         """;

    //     PromptTemplate template = new PromptTemplate(promptString);
    //     template.add("knowledge", systemKnowledge);
    //     template.add("question", userQuestion);

    //     // return chatClient.prompt(template.create()).call().content();

    //     return chatClient.prompt(template.create())
    //             .functions("bmiCalculator") // <--- THE MAGIC WIRING
    //             .call()
    //             .content();
    // }

    // 2. Add 'userId' so the memory knows WHO is talking
    public String askNutritionist(String userQuestion, String userId) {
        
        // RAG Step: Search ONLY the nutrition_vectors table
        List<Document> similarData = nutritionStore.similaritySearch(userQuestion);
        String systemKnowledge = similarData.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        // 3. The Fluent API: Combines RAG Context, Memory, and Tools flawlessly
        return chatClient.prompt()
                .system(sp -> sp.text("""
                    You are the official DietEase AI Nutritionist. 
                    Answer the user's question using ONLY the following verified database information:
                    {knowledge}
                    
                    If the information does not contain the answer, politely say you don't have that data.
                    """)
                    .param("knowledge", systemKnowledge)) // Injects your RAG data
                .user(userQuestion)
                // Isolates the memory to this specific user
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, userId))
                // Equips your math tool!
                .functions("bmiCalculator")
                .call()
                .content();
    }
    // 1. Change the return type from String to Flux<String>
    public reactor.core.publisher.Flux<String> askNutritionistStream(String userQuestion, String userId) {
        
        // RAG Step: Search ONLY the nutrition_vectors table
        List<Document> similarData = nutritionStore.similaritySearch(userQuestion);
        String systemKnowledge = similarData.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        return chatClient.prompt()
                .system(sp -> sp.text("""
                    You are the official DietEase AI Nutritionist. 
                    Answer the user's question using ONLY the following verified database information:
                    {knowledge}
                    """)
                    .param("knowledge", systemKnowledge))
                .user(userQuestion)
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, userId))
                .functions("bmiCalculator")
                // 2. THE UPGRADE: Swap .call() for .stream()
                .stream() 
                .content();
    }

    // public String askDevOpsLead(String userQuestion) {
    //     // 1. Search only the DevOps database
    //     List<Document> similarData = devOpsStore.similaritySearch(userQuestion);
        
    //     String systemKnowledge = similarData.stream()
    //             .map(Document::getContent)
    //             .collect(Collectors.joining("\n"));

    //     // 2. The Tech Lead Persona Prompt
    //     String promptString = """
    //         You are a Senior Java/Spring Boot Architect debugging the DietEase platform. 
    //         Use the following internal application code and logs to answer the developer's question. 
    //         If the code does not contain the answer, say "I cannot find that in the provided architecture."
            
    //         Internal Codebase Context:
    //         {knowledge}
            
    //         Developer Question: {question}
    //         """;

    //     PromptTemplate template = new PromptTemplate(promptString);
    //     template.add("knowledge", systemKnowledge);
    //     template.add("question", userQuestion);

    //     return chatClient.prompt(template.create()).call().content();
    // }
    // 4. Update DevOps Lead to also use the modern API and Memory
    public String askDevOpsLead(String userQuestion, String userId) {
        
        // RAG Step: Search only the DevOps database
        List<Document> similarData = devOpsStore.similaritySearch(userQuestion);
        String systemKnowledge = similarData.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        return chatClient.prompt()
                .system(sp -> sp.text("""
                    You are a Senior Java/Spring Boot Architect debugging the DietEase platform. 
                    Use the following internal application code and logs to answer the developer's question. 
                    If the code does not contain the answer, say "I cannot find that in the provided architecture."
                    
                    Internal Codebase Context:
                    {knowledge}
                    """)
                    .param("knowledge", systemKnowledge)) // Injects your RAG data
                .user(userQuestion)
                // Gives the DevOps lead memory too!
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, userId))
                .call()
                .content();
    }
}