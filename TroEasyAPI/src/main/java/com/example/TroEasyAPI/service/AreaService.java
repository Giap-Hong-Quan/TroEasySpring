package com.example.TroEasyAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TroEasyAPI.dto.areadto.AreaRequest;
import com.example.TroEasyAPI.dto.areadto.AreaResponse;
import com.example.TroEasyAPI.entity.Area;
import com.example.TroEasyAPI.exception.ConflictException;
import com.example.TroEasyAPI.exception.NotFoundException;
import com.example.TroEasyAPI.mapper.AreaMapper;
import com.example.TroEasyAPI.repository.AreaRepository;


import lombok.RequiredArgsConstructor;
@Service 
@RequiredArgsConstructor 
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;
    //post
    public AreaResponse createArea(AreaRequest areaRequest) {
        boolean exitArea = areaRepository.existsByName(areaRequest.name());
        if (exitArea) {
            throw new ConflictException("Khu trọ đã tồn tại!");
        }

        Area area = areaMapper.toEntity(areaRequest);
        Area saveArea = areaRepository.save(area);
        return areaMapper.toResponse(saveArea);
    }

    // GET: Lấy toàn bộ danh sách khu trọ
    public List<AreaResponse> getAllAreas() {
        return areaRepository.findAll()
                .stream()
                .map(areaMapper::toResponse)
                .toList();
    }

    // GET: Lấy chi tiết 1 khu trọ theo ID
    public AreaResponse getAreaById(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Khu trọ không tồn tại!"));
        return areaMapper.toResponse(area);
    }

    // DELETE: Xóa khu trọ theo ID
    public void deleteArea(Long id) {
        if (!areaRepository.existsById(id)) {
            throw new NotFoundException("Khu trọ không tồn tại!");
        }
        areaRepository.deleteById(id);
    }

    // PUT: Cập nhật thông tin khu trọ
    public AreaResponse updateArea(Long id, AreaRequest areaRequest) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Khu trọ không tồn tại!"));

        // Nếu đổi tên khu trọ, check xem tên mới có trùng với khu khác không
        if (!area.getName().equals(areaRequest.name()) && areaRepository.existsByName(areaRequest.name())) {
            throw new ConflictException("Tên khu trọ đã tồn tại!");
        }
        // Đè dữ liệu mới từ request vào area cũ
        areaMapper.updateArea(areaRequest, area);

        // Lưu lại xuống DB
        Area updatedArea = areaRepository.save(area);

        return areaMapper.toResponse(updatedArea);
    }
}
