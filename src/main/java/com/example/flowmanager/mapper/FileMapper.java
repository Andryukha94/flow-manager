package com.example.flowmanager.mapper;

import com.example.flowmanager.dto.response.FileStatusResponse;
import com.example.flowmanager.dto.response.UploadResponse;
import com.example.flowmanager.entity.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    UploadResponse toUploadResponse(FileEntity e);
    FileStatusResponse toStatusResponse(FileEntity e);
}