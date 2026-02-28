package com.example.flowmanager.messaging.kafka;

import com.example.flowmanager.dto.event.ConvertResultEvent;
import com.example.flowmanager.service.command.FileCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertResultListener {

    private final FileCommandService fileCommandService;

    @KafkaListener(
            topics = "${app.kafka.convert-result-topic}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onConvertSuccess(ConvertResultEvent event) {
        fileCommandService.handleConvertResult(event);
    }

    @KafkaListener(
            topics = "${app.kafka.convert-error-topic}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onConvertError(ConvertResultEvent event) {
        fileCommandService.handleConvertResult(event);
    }
}