package com.example.flowmanager.dto.event;

public record ConvertRequestEvent(
        String correlationId,
        String bucket,
        String objectKey
) {}