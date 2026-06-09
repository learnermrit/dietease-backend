package com.mrityunjay.dietease.controller;

import com.mrityunjay.dietease.service.DietAiChatService;
import com.mrityunjay.dietease.service.RagIngestionService;

import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;
    

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    @Autowired
    private RagIngestionService ingestionService;

    @Autowired
    private DietAiChatService chatService;

    // 🧠 1. Endpoint to TEACH the AI (Ingestion)
    @PostMapping("/ingest/nutrition")
    public ResponseEntity<String> teachNutritionBrain(@RequestBody Map<String, String> payload) {
        String textInfo = payload.get("knowledge");
        ingestionService.ingestNutritionKnowledge(textInfo);
        
        return ResponseEntity.ok("Successfully injected new knowledge into the nutrition vector table!");
    }

    // 🗣️ 2. Endpoint to ASK the AI (RAG Chat + Memory + BMI Tool)
    @PostMapping("/chat/nutrition")
    public ResponseEntity<Map<String, String>> askNutritionist(
            @RequestBody Map<String, String> payload,
            @RequestParam(defaultValue = "test-user-123") String userId) { // <-- Added Session Memory ID
        
        String userQuestion = payload.get("question");
        
        // Pass BOTH the question and the ID down to the service
        String aiResponse = chatService.askNutritionist(userQuestion, userId);
        
        return ResponseEntity.ok(Map.of("answer", aiResponse));
    }

    
   // 🌊 Endpoint to STREAM the AI response word-by-word
    @PostMapping(value = "/chat/nutrition/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> askNutritionistStream(
            @RequestBody Map<String, String> payload,
            @RequestParam(defaultValue = "test-stream-123") String userId) { 
        
        String userQuestion = payload.get("question");
        
        // We return the Flux directly! Spring WebFlux handles keeping the connection open.
        return chatService.askNutritionistStream(userQuestion, userId);
    }

    // 🧠 3. Endpoint to UPLOAD Java Code (DevOps Ingestion)
    @PostMapping("/ingest/devops")
    public ResponseEntity<String> teachDevOpsBrain(@RequestBody Map<String, String> payload) {
        String codeSnippet = payload.get("knowledge");
        ingestionService.ingestDevOpsKnowledge(codeSnippet);
        
        return ResponseEntity.ok("Successfully injected application code into the DevOps vector table!");
    }

    // 🗣️ 4. Endpoint to DEBUG with the AI (DevOps Chat + Memory)
    @PostMapping("/chat/devops")
    public ResponseEntity<Map<String, String>> askDevOpsLead(
            @RequestBody Map<String, String> payload,
            @RequestParam(defaultValue = "test-dev-123") String userId) { // <-- Added Session Memory ID
        
        String userQuestion = payload.get("question");
        
        // Pass BOTH the question and the ID down to the service
        String aiResponse = chatService.askDevOpsLead(userQuestion, userId);
        
        return ResponseEntity.ok(Map.of("answer", aiResponse));
    }
}