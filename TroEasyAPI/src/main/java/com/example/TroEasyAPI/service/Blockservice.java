package com.example.TroEasyAPI.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TroEasyAPI.dto.blockdto.BlockRequest;
import com.example.TroEasyAPI.dto.blockdto.BlockResponse;
import com.example.TroEasyAPI.entity.Area;
import com.example.TroEasyAPI.entity.Block;
import com.example.TroEasyAPI.exception.ConflictException;
import com.example.TroEasyAPI.exception.NotFoundException;
import com.example.TroEasyAPI.mapper.BlockMapper;
import com.example.TroEasyAPI.repository.AreaRepository;
import com.example.TroEasyAPI.repository.BlockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockService {

    private final BlockRepository blockRepository;
    private final AreaRepository areaRepository;
    private final BlockMapper blockMapper;

    // POST: Tạo dãy trọ mới
    public BlockResponse createBlock(BlockRequest request) {
        // Kiểm tra khu trọ (Area) có tồn tại không
        Area area = areaRepository.findById(request.areaId())
                .orElseThrow(() -> new NotFoundException("Khu trọ không tồn tại!"));

        // Kiểm tra tên dãy đã tồn tại trong cùng khu trọ này chưa
        if (blockRepository.existsByNameAndAreaId(request.name(), request.areaId())) {
            throw new ConflictException("Tên dãy đã tồn tại trong khu trọ này!");
        }
        // Map từ DTO sang Entity và gắn đối tượng Area vào
        Block block = blockMapper.toEntity(request);
        block.setArea(area);

        // Lưu xuống Database
        Block savedBlock = blockRepository.save(block);

        // Trả về DTO Response
        return blockMapper.toResponse(savedBlock);
    }

    // GET: Lấy danh sách dãy trọ theo khu trọ (areaId)
    public List<BlockResponse> getBlocksByAreaId(Long areaId) {
        // Kiểm tra khu trọ có tồn tại không
        if (!areaRepository.existsById(areaId)) {
            throw new NotFoundException("Khu trọ không tồn tại!");
        }

        List<Block> blocks = blockRepository.findByAreaId(areaId);
        return blockMapper.toResponseList(blocks);
    }

    // DELETE: Xóa dãy trọ theo ID
    public void deleteBlock(Long id) {
        if (!blockRepository.existsById(id)) {
            throw new NotFoundException("Dãy trọ không tồn tại!");
        }
        blockRepository.deleteById(id);
    }

    // PUT: Cập nhật thông tin dãy trọ
    public BlockResponse updateBlock(Long id, BlockRequest request) {
        // 1. Tìm dãy trọ cũ
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dãy trọ không tồn tại!"));

        // 2. Nếu đổi tên, kiểm tra tên mới có bị trùng trong khu trọ đó không
        if (!block.getName().equals(request.name())
                && blockRepository.existsByNameAndAreaId(request.name(), block.getArea().getId())) {
            throw new ConflictException("Tên dãy đã tồn tại trong khu trọ này!");
        }

        // 3. Đè dữ liệu mới vào entity cũ
        blockMapper.updateBlock(request, block);

        // 4. Lưu lại xuống DB
        Block updatedBlock = blockRepository.save(block);

        // 5. Trả về kết quả
        return blockMapper.toResponse(updatedBlock);
    }
}
