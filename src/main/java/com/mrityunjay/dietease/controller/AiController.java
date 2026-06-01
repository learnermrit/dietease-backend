package com.mrityunjay.dietease.controller;

import com.mrityunjay.dietease.service.DietAiChatService;
import com.mrityunjay.dietease.service.RagIngestionService;
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

    // 🗣️ 2. Endpoint to ASK the AI (RAG Chat)
    @PostMapping("/chat/nutrition")
    public ResponseEntity<Map<String, String>> askNutritionist(@RequestBody Map<String, String> payload) {
        String userQuestion = payload.get("question");
        
        // This triggers the RAG Pipeline (Retrieve from DB -> Send to Gemini)
        String aiResponse = chatService.askNutritionist(userQuestion);
        
        return ResponseEntity.ok(Map.of("answer", aiResponse));
    }
}