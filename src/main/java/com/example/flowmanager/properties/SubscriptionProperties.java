package com.example.flowmanager.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.subscription")
public record SubscriptionProperties(
        String baseUrl,
        long cacheTtlMinutes
) {
}