package com.example.flowmanager.dto.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertRequestEvent {
    private String correlationId;
    private String bucket;
    private String objectKey;
}
