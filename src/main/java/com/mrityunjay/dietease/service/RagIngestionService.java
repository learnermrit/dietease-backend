package com.mrityunjay.dietease.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagIngestionService {

    @Autowired
    @Qualifier("nutritionVectorStore")
    private VectorStore nutritionStore;

    @Autowired
    @Qualifier("devOpsVectorStore")
    private VectorStore devOpsStore;

    public void ingestNutritionKnowledge(String textInfo) {
        Document document = new Document(textInfo);
        nutritionStore.add(List.of(document)); 
    }

    public void ingestDevOpsKnowledge(String codeOrLogs) {
        Document document = new Document(codeOrLogs);
        devOpsStore.add(List.of(document));
    }
}