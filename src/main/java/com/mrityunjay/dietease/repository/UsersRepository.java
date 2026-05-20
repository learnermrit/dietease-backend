package com.mrityunjay.dietease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrityunjay.dietease.entity.Users;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
   Optional<Users> findByEmail(String email);
    Users findByPhone(String phone);
}
