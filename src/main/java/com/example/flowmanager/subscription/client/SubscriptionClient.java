package com.example.flowmanager.subscription.client;

import com.example.flowmanager.properties.SubscriptionProperties;
import com.example.flowmanager.subscription.dto.SubscriptionDto;
import com.example.flowmanager.subscription.dto.SubscriptionType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SubscriptionClient {

    private final RestClient restClient;

    public SubscriptionClient(SubscriptionProperties props) {
        this.restClient = RestClient.builder()
                .baseUrl(props.baseUrl())
                .build();
    }

    public SubscriptionDto getByLogin(String login) {
        try {
            return restClient.get()
                    .uri("/subscriptions/{login}", login)
                    .retrieve()
                    .body(SubscriptionDto.class);
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            return new SubscriptionDto(login, SubscriptionType.FREE, null);
        }
    }
}