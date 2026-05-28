package com.mrityunjay.dietease.dto;

public class GroceryItemDTO {
    private String ingredientName;
    private Double totalQuantity;
    private String unit;

    public GroceryItemDTO(String ingredientName, Double totalQuantity, String unit) {
        this.ingredientName = ingredientName;
        this.totalQuantity = totalQuantity;
        this.unit = unit;
    }

    // Generate Getters and Setters
    public String getIngredientName() { return ingredientName; }
    public Double getTotalQuantity() { return totalQuantity; }
    public String getUnit() { return unit; }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}