package com.example.TroEasyAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TroEasyAPI.entity.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {

    // Kiểm tra trùng tên dãy trong cùng 1 khu trọ
    boolean existsByNameAndAreaId(String name, Long areaId);

    // Lấy danh sách dãy thuộc về 1 khu trọ
    List<Block> findByAreaId(Long areaId);
}
