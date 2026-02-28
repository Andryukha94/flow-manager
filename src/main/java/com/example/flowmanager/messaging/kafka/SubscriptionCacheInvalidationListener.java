package com.example.flowmanager.messaging.kafka;

import com.example.flowmanager.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionCacheInvalidationListener {

    private final SubscriptionService subscriptionService;

    @KafkaListener(
            topics = "${app.kafka.subscription-invalidate-topic}",
            containerFactory = "subscriptionInvalidateKafkaListenerContainerFactory"
    )
    public void onInvalidate(String login) {
        subscriptionService.evict(login);
        log.info("Kafka invalidate received login={}", login);

    }
}