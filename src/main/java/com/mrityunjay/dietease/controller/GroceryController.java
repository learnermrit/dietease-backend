package com.mrityunjay.dietease.controller;

import com.mrityunjay.dietease.dto.FoodItemInputDTO;
import com.mrityunjay.dietease.dto.GroceryItemDTO;
import com.mrityunjay.dietease.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groceries")
public class GroceryController {

    @Autowired
    private GroceryService groceryService; // Hiring the calculation engine

    // Endpoint to generate a grocery list from a list of planned meals
    @PostMapping("/generate")
    public ResponseEntity<List<GroceryItemDTO>> generateList(@RequestBody List<FoodItemInputDTO> plannedMeals) {
        
        // Hand the raw JSON payload to the engine
        List<GroceryItemDTO> groceryList = groceryService.generateGroceryList(plannedMeals);
        
        // Return the clean, aggregated list as a 200 OK
        return ResponseEntity.ok(groceryList);
    }
}