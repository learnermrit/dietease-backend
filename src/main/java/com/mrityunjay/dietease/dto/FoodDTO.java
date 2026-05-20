package com.mrityunjay.dietease.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FoodDTO {
    private Long foodId;
    private String foodname;
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal carbs;
    private BigDecimal fat;
    private Boolean isVeg;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public FoodDTO() {
    }
    public FoodDTO(Long foodId, String foodname, BigDecimal calories, BigDecimal protein, BigDecimal carbs,
            BigDecimal fat, Boolean isVeg, String description, LocalDateTime createdAt) {
        this.foodId = foodId;
        this.foodname = foodname;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.isVeg = isVeg;
        this.description = description;
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Long getFoodId() {
        return foodId;
    }
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }
    public String getFoodname() {
        return foodname;
    }
    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
    public BigDecimal getCalories() {
        return calories;
    }
    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }
    public BigDecimal getProtein() {
        return protein;
    }
    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }
    public BigDecimal getCarbs() {
        return carbs;
    }
    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }
    public BigDecimal getFat() {
        return fat;
    }
    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }
    public Boolean getIsVeg() {
        return isVeg;
    }
    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
}
