package com.mrityunjay.dietease.dto;

public class FoodItemInputDTO {
    private Long foodId;
    private Integer portions; // e.g., eating this food 3 times this week

    // Generate Getters and Setters
    public Long getFoodId() { return foodId; }
    public void setFoodId(Long foodId) { this.foodId = foodId; }
    public Integer getPortions() { return portions; }
    public void setPortions(Integer portions) { this.portions = portions; }
}