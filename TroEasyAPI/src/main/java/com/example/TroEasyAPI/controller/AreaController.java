package com.example.TroEasyAPI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TroEasyAPI.common.ApiResponse;
import com.example.TroEasyAPI.dto.areadto.AreaRequest;
import com.example.TroEasyAPI.dto.areadto.AreaResponse;
import com.example.TroEasyAPI.service.AreaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/area")
@RequiredArgsConstructor 
public class AreaController {
    private final AreaService areaService;

    @PostMapping
    public ResponseEntity<ApiResponse<AreaResponse>> createArea(@Valid @RequestBody AreaRequest areaRequest) {
        AreaResponse createdArea = areaService.createArea(areaRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo khu vực thành công", createdArea));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AreaResponse>>> getAllAreas() {
        List<AreaResponse> areas = areaService.getAllAreas();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách khu vực thành công", areas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AreaResponse>> getAreaById(@PathVariable Long id) {
        AreaResponse area = areaService.getAreaById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin khu vực thành công", area));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa khu vực thành công"));
    }
}
