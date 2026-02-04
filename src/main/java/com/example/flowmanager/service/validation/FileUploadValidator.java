package com.example.flowmanager.service.validation;

import com.example.flowmanager.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadValidator {

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException();
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new BadRequestException();
        }
    }
}