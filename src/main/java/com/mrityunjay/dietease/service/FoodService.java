package com.mrityunjay.dietease.service;


import java.util.List;

import com.mrityunjay.dietease.dto.FoodDTO;
import com.mrityunjay.dietease.entity.Foods;

public interface FoodService {
    List<FoodDTO> getAllFoods();
    String bulkImportTest();
    String bulkImportThreadPool();
    List<Foods> searchFoodsByKeyword(String keyword);
    List<Foods> getOnlyVegetarianFoods();
}
