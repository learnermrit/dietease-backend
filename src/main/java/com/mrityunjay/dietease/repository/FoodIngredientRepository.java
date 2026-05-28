package com.mrityunjay.dietease.repository;

import com.mrityunjay.dietease.entity.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, Long> {
    
    // SPRING MAGIC: Finds every ingredient mapping for a collection of Food IDs in a single query
    List<FoodIngredient> findByFoodFoodIdIn(List<Long> foodIds);
}