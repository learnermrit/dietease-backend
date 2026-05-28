package com.mrityunjay.dietease.service;

import com.mrityunjay.dietease.dto.FoodItemInputDTO;
import com.mrityunjay.dietease.dto.GroceryItemDTO;
import com.mrityunjay.dietease.entity.FoodIngredient;
import com.mrityunjay.dietease.repository.FoodIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroceryServiceImpl implements GroceryService {

    @Autowired
    private FoodIngredientRepository foodIngredientRepository;

    @Override
    public List<GroceryItemDTO> generateGroceryList(List<FoodItemInputDTO> plannedMeals) {
        
        // 1. Create a fast lookup map of FoodId -> Portions chosen by user
        Map<Long, Integer> portionMap = plannedMeals.stream()
                .collect(Collectors.toMap(FoodItemInputDTO::getFoodId, FoodItemInputDTO::getPortions));

        // 2. Extract all the Food IDs requested
        List<Long> foodIds = plannedMeals.stream()
                .map(FoodItemInputDTO::getFoodId)
                .collect(Collectors.toList());

        // 3. Query the database to find out WHAT raw ingredients make up those foods
        List<FoodIngredient> recipeIngredients = foodIngredientRepository.findByFoodFoodIdIn(foodIds);

        // 4. The Aggregation Matrix (The Math Engine)
        // Group everything by a unique key combined of "Ingredient Name + Unit Type"
        Map<String, List<FoodIngredient>> groupedByIngredient = recipeIngredients.stream()
                .collect(Collectors.groupingBy(fi -> fi.getIngredient().getIngredientName() + " (" + fi.getUnit() + ")"));

        // 5. Turn the grouped chunks into single aggregated line items
        return groupedByIngredient.entrySet().stream().map(entry -> {
            String key = entry.getKey();
            List<FoodIngredient> instances = entry.getValue();

            // Extract data from the first instance for metadata
            String name = instances.get(0).getIngredient().getIngredientName();
            String unit = instances.get(0).getUnit();

            // Mathematically multiply the baseline recipe quantity by the user's chosen portions and sum them up
            Double finalTotalQuantity = instances.stream()
                    .mapToDouble(fi -> {
                        Integer userPortions = portionMap.getOrDefault(fi.getFood().getFoodId(), 1);
                        return fi.getQuantity() * userPortions;
                    })
                    .sum();

            return new GroceryItemDTO(name, finalTotalQuantity, unit);
        }).collect(Collectors.toList());
    }
}