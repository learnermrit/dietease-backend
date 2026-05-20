package com.mrityunjay.dietease.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_list")
public class Foods {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "food_id")
private Long foodId;

@Column(name = "food_name")
private String foodname;


@Column(name = "calories", precision = 10, scale = 2)
private BigDecimal calories;

@Column(name = "protein", precision = 10, scale = 2)
private BigDecimal protein;

@Column(name = "carbs", precision = 10, scale = 2)
private BigDecimal carbs;

@Column(name = "fat", precision = 10, scale = 2)
private BigDecimal fat;

@Column(name = "is_veg")
private Boolean isVeg;

@Column(name = "description")
private String description;

@Column(name = "created_at")
private LocalDateTime createdAt;

public Foods(){}

public Foods(String foodname, BigDecimal calories, BigDecimal protein, BigDecimal carbs, BigDecimal fat,
        Boolean isVeg, String description) {
  
    this.foodname = foodname;
    this.calories = calories;
    this.protein = protein;
    this.carbs = carbs;
    this.fat = fat;
    this.isVeg = isVeg;
    this.description = description;
   
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

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

@Override
public String toString(){
    return "Foods{" +
            "foodId=" + foodId +
            ", foodname='" + foodname + '\'' +
            ", calories=" + calories +
            ", protein=" + protein +
            ", carbs=" + carbs +
            ", fat=" + fat +
            ", isVeg=" + isVeg +
            ", description='" + description + '\'' +
            ", createdAt=" + createdAt +
            '}';

    
}}
