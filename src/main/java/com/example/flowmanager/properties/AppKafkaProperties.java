package com.example.flowmanager.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
public record AppKafkaProperties(
        String subscriptionInvalidateGroupId
) {}