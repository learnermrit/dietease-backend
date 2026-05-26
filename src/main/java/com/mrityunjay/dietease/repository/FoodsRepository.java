package com.mrityunjay.dietease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrityunjay.dietease.entity.Foods;

@Repository
public interface FoodsRepository extends JpaRepository<Foods, Long> {

    // Spring writes the SQL to find foods by vegetarian status
    List<Foods> findByIsVeg(Boolean isVeg);

    // Spring writes the SQL to search for foods by name (LIKE %keyword%)
    List<Foods> findByFoodnameContainingIgnoreCase(String keyword);
}
