package com.example.flowmanager.service.command;

import com.example.flowmanager.dto.event.ConvertRequestEvent;
import com.example.flowmanager.dto.event.ConvertResultEvent;
import com.example.flowmanager.entity.FileEntity;
import com.example.flowmanager.entity.FileStatus;
import com.example.flowmanager.exception.FileNotFoundException;
import com.example.flowmanager.exception.FileProcessingException;
import com.example.flowmanager.exception.MinioUnavailableException;
import com.example.flowmanager.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileCommandService {

    private final FileRepository repo;
    private final MinioStorageService minioStorageService;
    private final KafkaProducerService kafkaProducerService;

    public FileEntity uploadAndSend(byte[] bytes, String originalFilename, String contentType) {
        FileEntity entity = null;

        try {
            String objectKey = buildObjectKey(originalFilename);

            entity = FileEntity.builder()
                    .originalBucket(minioStorageService.getIncomingBucket())
                    .originalKey(objectKey)
                    .status(FileStatus.PROCESSING)
                    .build();

            entity = repo.save(entity);

            minioStorageService.uploadIncoming(objectKey, bytes, contentType);

            ConvertRequestEvent event = new ConvertRequestEvent(
                    String.valueOf(entity.getId()),
                    entity.getOriginalBucket(),
                    entity.getOriginalKey()
            );

            kafkaProducerService.sendConvertRequest(event);

            return entity;

        } catch (MinioUnavailableException e) {
            throw e;

        } catch (Exception e) {
            if (entity != null && entity.getId() != null) {
                entity.setStatus(FileStatus.FAILED);
                entity.setError(e.getClass().getSimpleName());
                repo.save(entity);
            }

            throw (e instanceof FileProcessingException)
                    ? (FileProcessingException) e
                    : new FileProcessingException(e);
        }
    }

    @Transactional
    public void handleConvertResult(ConvertResultEvent event) {
        applyResult(event);
    }

    private void applyResult(ConvertResultEvent event) {
        Long id = Long.valueOf(event.getCorrelationId());

        FileEntity entity = repo.findById(id)
                .orElseThrow(FileNotFoundException::new);

        FileStatus current = entity.getStatus();
        if (current == FileStatus.SUCCESS || current == FileStatus.FAILED) {
            return;
        }

        if (ConvertResultEvent.STATUS_SUCCESS.equals(event.getStatus())) {
            entity.setStatus(FileStatus.SUCCESS);
            entity.setResultBucket(event.getBucket());
            entity.setResultKey(event.getObjectKey());
            entity.setError(null);
        } else {
            entity.setStatus(FileStatus.FAILED);
            entity.setError(event.getError());
        }

        repo.save(entity);
    }

    private String buildObjectKey(String originalFilename) {
        String safeName = (originalFilename == null || originalFilename.isBlank())
                ? "file"
                : originalFilename.replaceAll("[\\\\/]", "_");
        return UUID.randomUUID() + "_" + safeName;
    }
}