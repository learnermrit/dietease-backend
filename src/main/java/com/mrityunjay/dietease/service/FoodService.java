package com.mrityunjay.dietease.service;


import java.util.List;

import com.mrityunjay.dietease.dto.FoodDTO;

public interface FoodService {
    List<FoodDTO> getAllFoods();
    String bulkImportTest();
    String bulkImportThreadPool();
}
