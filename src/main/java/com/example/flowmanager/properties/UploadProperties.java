package com.example.flowmanager.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.upload")
public record UploadProperties(
        long freeMaxBytes,
        long paidMaxBytes
) {}