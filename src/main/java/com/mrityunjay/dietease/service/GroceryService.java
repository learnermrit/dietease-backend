package com.mrityunjay.dietease.service;

import com.mrityunjay.dietease.dto.FoodItemInputDTO;
import com.mrityunjay.dietease.dto.GroceryItemDTO;
import java.util.List;

public interface GroceryService {
    List<GroceryItemDTO> generateGroceryList(List<FoodItemInputDTO> plannedMeals);
}