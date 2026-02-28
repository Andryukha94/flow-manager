package com.example.flowmanager.config;

import com.example.flowmanager.exception.MinioUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class KafkaErrorHandlingConfig {

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) -> new TopicPartition(record.topic() + ".DLT", record.partition())
                );

        FixedBackOff backOff = new FixedBackOff(1000L, 5L);

        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);

        handler.addRetryableExceptions(MinioUnavailableException.class);

        handler.addNotRetryableExceptions(
                IllegalArgumentException.class
        );

        handler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.warn(
                    "Retry attempt {} for topic={}, partition={}, offset={}, key={}, exception={}",
                    deliveryAttempt,
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    record.key(),
                    ex.getClass().getSimpleName()
            );
        });

        return handler;
    }
}