package com.example.flowmanager.service.api;

import com.example.flowmanager.dto.response.FileStatusResponse;
import com.example.flowmanager.dto.response.UploadResponse;
import com.example.flowmanager.entity.FileEntity;
import com.example.flowmanager.mapper.FileMapper;
import com.example.flowmanager.service.command.FileCommandService;
import com.example.flowmanager.service.query.FileQueryService;
import com.example.flowmanager.service.validation.FileUploadValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileApiService {

    private final FileUploadValidator validator;
    private final FileCommandService commandService;
    private final FileQueryService queryService;
    private final FileMapper mapper;

    public UploadResponse upload(MultipartFile file) {
        validator.validate(file);

        byte[] bytes = com.example.flowmanager.util.FileUtils.toBytes(file);
        String contentType = com.example.flowmanager.util.FileUtils.contentTypeOrDefault(file);

        FileEntity entity = commandService.uploadAndSend(bytes, file.getOriginalFilename(), contentType);
        return mapper.toUploadResponse(entity);
    }

    public FileStatusResponse status(Long id) {
        FileEntity entity = queryService.getById(id);
        return mapper.toStatusResponse(entity);
    }

    public ResponseEntity<byte[]> download(Long id) {
        byte[] pdf = queryService.getResultPdfBytes(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"result.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
