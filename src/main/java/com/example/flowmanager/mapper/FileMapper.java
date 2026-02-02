package com.example.flowmanager.mapper;

import com.example.flowmanager.dto.response.FileStatusResponse;
import com.example.flowmanager.dto.response.UploadResponse;
import com.example.flowmanager.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public UploadResponse toUploadResponse(FileEntity e) {
        return UploadResponse.builder()
                .id(e.getId())
                .status(e.getStatus())
                .build();
    }

    public FileStatusResponse toStatusResponse(FileEntity e) {
        return FileStatusResponse.builder()
                .id(e.getId())
                .status(e.getStatus())
                .originalBucket(e.getOriginalBucket())
                .originalKey(e.getOriginalKey())
                .resultBucket(e.getResultBucket())
                .resultKey(e.getResultKey())
                .error(e.getError())
                .build();
    }
}
