package com.example.TroEasyAPI.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.TroEasyAPI.dto.blockdto.BlockRequest;
import com.example.TroEasyAPI.dto.blockdto.BlockResponse;
import com.example.TroEasyAPI.entity.Block;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    Block toEntity(BlockRequest request);

    @Mapping(target = "areaId", source = "area.id")
    BlockResponse toResponse(Block block);

    List<BlockResponse> toResponseList(List<Block> blocks);

    void updateBlock(BlockRequest request, @MappingTarget Block block);
}
