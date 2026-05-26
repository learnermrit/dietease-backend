package com.mrityunjay.dietease.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrityunjay.dietease.dto.FoodDTO;
import com.mrityunjay.dietease.entity.Foods;
import com.mrityunjay.dietease.repository.FoodsRepository;

import java.util.ArrayList;
//import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodsRepository foodsRepository;

    @Override
    public List<FoodDTO> getAllFoods(){
        return foodsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FoodDTO convertToDTO(Foods food){
        return new FoodDTO(
            food.getFoodId(),
            food.getFoodname(),
            food.getCalories(),
            food.getProtein(),
            food.getCarbs(),
            food.getFat(),
            food.getIsVeg(),
            food.getDescription(),
            food.getCreatedAt()
        );
    }

    @Override
    public String bulkImportTest() {
    long startTime = System.currentTimeMillis();
    int totalRecords = 50000;

    System.out.println("Starting Single-Threaded Bulk Import of " + totalRecords + " items...");

    for (int i = 1; i <= totalRecords; i++) {
        // 1. Create a dummy food item
        Foods food = new Foods();
        food.setFoodname("Bulk Apple " + i);
        food.setCalories(new BigDecimal("95"));
        food.setProtein(new BigDecimal("0.5"));
        food.setCarbs(new BigDecimal("25"));
        food.setFat(new BigDecimal("0.3"));
        food.setIsVeg(true);
        food.setDescription("Auto-generated FDA test data");
        // food.setCreatedAt(LocalDateTime.now()); // Assuming you added @PrePersist!

        // 2. THE BOTTLENECK: Opening a DB connection and saving ONE item at a time
        foodsRepository.save(food);
    }

    long endTime = System.currentTimeMillis();
    long timeTakenSeconds = (endTime - startTime) / 1000;

    return "Saved " + totalRecords + " items in " + timeTakenSeconds + " seconds using a single thread.";
}

@Override
public String bulkImportThreadPool() {
    long startTime = System.currentTimeMillis();
    int totalRecords = 50000;
    int numberOfThreads = 10; 
    int batchSize = totalRecords / numberOfThreads; // 5,000 items per thread

    // 1. Hire 10 background workers (The Thread Pool)
    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
    
    // A list to keep track of all our workers so we know when they are ALL done
    List<CompletableFuture<Void>> futures = new ArrayList<>();

    System.out.println("Starting Phase 3: Thread Pool Import...");

    // 2. Assign work to each of the 10 threads
    for (int i = 0; i < numberOfThreads; i++) {
        final int threadNumber = i; // Java requires variables inside lambdas to be final
        
        // Tell a thread to start working asynchronously
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            int start = (threadNumber * batchSize) + 1;
            int end = (threadNumber + 1) * batchSize;
            
            System.out.println("Worker " + Thread.currentThread().getName() + " processing items " + start + " to " + end);
            
            List<Foods> batchToSave = new ArrayList<>();
            
            for (int j = start; j <= end; j++) {
                Foods food = new Foods();
                food.setFoodname("Pool Apple " + j);
                food.setCalories(new BigDecimal("95"));
                food.setProtein(new BigDecimal("0.5"));
                food.setCarbs(new BigDecimal("25"));
                food.setFat(new BigDecimal("0.3"));
                food.setIsVeg(true);
                food.setDescription("Thread Pool Data");
                
                batchToSave.add(food); // Pack them in a box
            }
            
            // 3. THE OPTIMIZATION: Save all 5,000 at once instead of 1 by 1
            foodsRepository.saveAll(batchToSave); 
            
        }, executor);
        
        futures.add(future);
    }

    // 4. Wait for ALL 10 workers to finish their jobs before stopping the timer
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    
    // 5. Fire the workers (free up RAM)
    executor.shutdown();

    long endTime = System.currentTimeMillis();
    long timeTakenSeconds = (endTime - startTime) / 1000;

    return "Saved " + totalRecords + " items using 10 Threads in " + timeTakenSeconds + " seconds.";
}

    @Override
    public List<Foods> searchFoodsByKeyword(String keyword) {
        // You can add extra business logic here later (e.g., logging the search)
        return foodsRepository.findByFoodnameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Foods> getOnlyVegetarianFoods() {
        return foodsRepository.findByIsVeg(true);
    }
} 
