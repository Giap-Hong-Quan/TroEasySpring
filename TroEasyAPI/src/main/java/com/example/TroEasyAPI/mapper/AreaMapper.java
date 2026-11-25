package com.example.TroEasyAPI.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.TroEasyAPI.dto.areadto.AreaRequest;
import com.example.TroEasyAPI.dto.areadto.AreaResponse;
import com.example.TroEasyAPI.entity.Area;

@Mapper(componentModel = "spring")
public interface AreaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Area toEntity(AreaRequest request);

    AreaResponse toResponse(Area area);

    List<AreaResponse> toResponseList(List<Area> areas);
}
