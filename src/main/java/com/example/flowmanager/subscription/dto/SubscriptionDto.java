package com.example.flowmanager.subscription.dto;

import java.time.LocalDateTime;

public record SubscriptionDto(
        String login,
        SubscriptionType type,
        LocalDateTime expiresAt
) {}
