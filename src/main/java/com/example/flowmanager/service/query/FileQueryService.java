package com.example.flowmanager.service.query;

import com.example.flowmanager.entity.FileEntity;
import com.example.flowmanager.entity.FileStatus;
import com.example.flowmanager.exception.FileNotFoundException;
import com.example.flowmanager.exception.FileProcessingException;
import com.example.flowmanager.repository.FileRepository;
import com.example.flowmanager.service.command.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileQueryService {

    private final FileRepository repo;
    private final MinioStorageService minioStorageService;

    public FileEntity getById(Long id) {
        return repo.findById(id).orElseThrow(FileNotFoundException::new);
    }

    public byte[] getResultPdfBytes(Long id) {
        FileEntity entity = getById(id);

        if (entity.getStatus() != FileStatus.SUCCESS) {
            throw new FileProcessingException();
        }

        if (entity.getResultKey() == null || entity.getResultKey().isBlank()) {
            throw new FileProcessingException();
        }

        return minioStorageService.downloadOutgoing(entity.getResultKey());
    }
}