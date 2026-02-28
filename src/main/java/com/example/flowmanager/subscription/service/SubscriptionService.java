package com.example.flowmanager.subscription.service;

import com.example.flowmanager.subscription.client.SubscriptionClient;
import com.example.flowmanager.subscription.dto.SubscriptionDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    public static final String CACHE_NAME = "subscription";

    private final SubscriptionClient client;

    public SubscriptionService(SubscriptionClient client) {
        this.client = client;
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#login",
            condition = "#login != null && !#login.isBlank()")
    public SubscriptionDto getCached(String login) {
        return client.getByLogin(login);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#login",
            condition = "#login != null && !#login.isBlank()")
    public void evict(String login) {
    }
}