package com.example.flowmanager.controller;

import com.example.flowmanager.dto.response.FileStatusResponse;
import com.example.flowmanager.dto.response.UploadResponse;
import com.example.flowmanager.service.api.FileApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileApiService fileApiService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadResponse upload(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("X-User-Login") String login
    ) {
        return fileApiService.upload(file, login);
    }

    @GetMapping("/{id}/status")
    public FileStatusResponse status(@PathVariable Long id) {
        return fileApiService.status(id);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return fileApiService.download(id);
    }
}