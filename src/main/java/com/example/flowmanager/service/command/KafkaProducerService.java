package com.example.flowmanager.service.command;

import com.example.flowmanager.dto.event.ConvertRequestEvent;
import com.example.flowmanager.exception.FileProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Value("${app.kafka.convert-request-topic}")
    private String requestTopic;

    public void sendConvertRequest(ConvertRequestEvent event) {
        try {
            kafkaTemplate.send(requestTopic, event.correlationId(), event).get();
        } catch (Exception e) {
            throw new FileProcessingException(e);
        }
    }
}