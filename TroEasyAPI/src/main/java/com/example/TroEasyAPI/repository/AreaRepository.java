package com.example.TroEasyAPI.repository;

import com.example.TroEasyAPI.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
boolean existsByName(String name); 
}
