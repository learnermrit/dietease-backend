package com.mrityunjay.dietease.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {

    // 🧠 Brain 1: The Nutritionist (Table: nutrition_vectors)
    @Bean(name = "nutritionVectorStore")
    public VectorStore nutritionVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("nutrition_vectors") // M5 unlocks this!
                .dimensions(384)
                .initializeSchema(true)
                .build();
    }

    // 🧠 Brain 2: The App Flow Guide (Table: app_flow_vectors)
    @Bean(name = "appFlowVectorStore")
    public VectorStore appFlowVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("app_flow_vectors")
                .dimensions(384)
                .initializeSchema(true)
                .build();
    }

    // 🧠 Brain 3: The Developer Ops Support (Table: dev_ops_vectors)
    @Bean(name = "devOpsVectorStore")
    public VectorStore devOpsVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("dev_ops_vectors")
                .dimensions(384)
                .initializeSchema(true)
                .build();
    }
}