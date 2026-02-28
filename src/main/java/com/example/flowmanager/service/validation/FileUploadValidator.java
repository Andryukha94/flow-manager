package com.example.flowmanager.service.validation;

import com.example.flowmanager.exception.BadRequestException;
import com.example.flowmanager.exception.FileTooLargeException;
import com.example.flowmanager.properties.UploadProperties;
import com.example.flowmanager.subscription.dto.SubscriptionDto;
import com.example.flowmanager.subscription.dto.SubscriptionType;
import com.example.flowmanager.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FileUploadValidator {

    private final SubscriptionService subscriptionService;
    private final UploadProperties upload;

    public void validate(MultipartFile file, String login) {
        validateRequest(file, login);

        SubscriptionDto s = subscriptionService.getCached(login);
        LocalDateTime now = LocalDateTime.now();

        long maxBytes = isPaidActive(s, now)
                ? upload.paidMaxBytes()
                : upload.freeMaxBytes();

        if (file.getSize() > maxBytes) {
            throw new FileTooLargeException();
        }
    }

    private void validateRequest(MultipartFile file, String login) {
        if (file == null || file.isEmpty()) throw new BadRequestException();

        String name = file.getOriginalFilename();
        if (name == null || name.isBlank()) throw new BadRequestException();

        if (login == null || login.isBlank()) throw new BadRequestException();
    }

    private boolean isPaidActive(SubscriptionDto s, LocalDateTime now) {
        return s != null
                && s.type() == SubscriptionType.PAID
                && s.expiresAt() != null
                && s.expiresAt().isAfter(now);
    }
}