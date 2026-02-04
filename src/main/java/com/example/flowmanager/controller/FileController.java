package com.example.flowmanager.controller;

import com.example.flowmanager.dto.response.FileStatusResponse;
import com.example.flowmanager.dto.response.UploadResponse;
import com.example.flowmanager.entity.FileEntity;
import com.example.flowmanager.mapper.FileMapper;
import com.example.flowmanager.service.command.FileCommandService;
import com.example.flowmanager.service.query.FileQueryService;
import com.example.flowmanager.service.validation.FileUploadValidator;
import com.example.flowmanager.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileCommandService commandService;
    private final FileQueryService queryService;
    private final FileMapper mapper;
    private final FileUploadValidator validator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadResponse upload(@RequestPart("file") MultipartFile file) {

        validator.validate(file);

        byte[] bytes = FileUtils.toBytes(file);
        String contentType = FileUtils.contentTypeOrDefault(file);

        FileEntity entity = commandService.uploadAndSend(
                bytes,
                file.getOriginalFilename(),
                contentType
        );

        return mapper.toUploadResponse(entity);
    }

    @GetMapping("/{id}/status")
    public FileStatusResponse status(@PathVariable Long id) {
        return mapper.toStatusResponse(queryService.getById(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        byte[] pdf = queryService.getResultPdfBytes(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"result.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}