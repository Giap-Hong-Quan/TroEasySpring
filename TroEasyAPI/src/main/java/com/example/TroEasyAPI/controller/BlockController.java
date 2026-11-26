package com.example.TroEasyAPI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TroEasyAPI.common.ApiResponse;
import com.example.TroEasyAPI.dto.blockdto.BlockRequest;
import com.example.TroEasyAPI.dto.blockdto.BlockResponse;
import com.example.TroEasyAPI.service.BlockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping
    public ResponseEntity<ApiResponse<BlockResponse>> createBlock(@Valid @RequestBody BlockRequest request) {
        BlockResponse response = blockService.createBlock(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo dãy trọ thành công", response));
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<ApiResponse<List<BlockResponse>>> getBlocksByAreaId(@PathVariable Long areaId) {
        List<BlockResponse> responses = blockService.getBlocksByAreaId(areaId);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách dãy trọ theo khu thành công", responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa dãy trọ thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BlockResponse>> updateBlock(
            @PathVariable Long id,
            @Valid @RequestBody BlockRequest request) {
        BlockResponse response = blockService.updateBlock(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật dãy trọ thành công", response));
    }
}
