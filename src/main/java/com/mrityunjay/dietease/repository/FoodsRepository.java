package com.mrityunjay.dietease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrityunjay.dietease.entity.Foods;

@Repository
public interface FoodsRepository extends JpaRepository<Foods, Long> {
}
