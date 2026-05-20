 package com.mrityunjay.dietease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mrityunjay.dietease.dto.FoodDTO;
import com.mrityunjay.dietease.service.FoodService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 @RestController
 @RequestMapping("/api/v1/foods")
 public class FoodController{

    @Autowired
    private FoodService foodService;

    @GetMapping("/hello")
    public String hello(){
        return "return hello from food service";
    }

     //GET all foods
    @GetMapping("/foodsList")
    public ResponseEntity<List<FoodDTO>> getAllFoods() {
        List<FoodDTO> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @PostMapping("/bulk-import/test")
public ResponseEntity<Map<String, String>> triggerBulkImport() {
    String resultMessage = foodService.bulkImportTest();
    
    Map<String, String> response = new HashMap<>();
    response.put("message", resultMessage);
    
    return ResponseEntity.ok(response);
}

@PostMapping("/bulk-import/pool")
public ResponseEntity<Map<String, String>> triggerPoolImport() {
    String resultMessage = foodService.bulkImportThreadPool();
    
    Map<String, String> response = new HashMap<>();
    response.put("message", resultMessage);
    
    return ResponseEntity.ok(response);
}
 }
