package com.example.flowmanager.dto.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResultEvent {

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    private String bucket;
    private String objectKey;
    private String correlationId;
    private String status;
    private String error;
}