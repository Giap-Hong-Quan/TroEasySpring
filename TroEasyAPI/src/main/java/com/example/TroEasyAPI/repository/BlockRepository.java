package com.example.TroEasyAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TroEasyAPI.entity.Block;

public interface BlockRepository extends JpaRepository<Block,Long> {
    
}
