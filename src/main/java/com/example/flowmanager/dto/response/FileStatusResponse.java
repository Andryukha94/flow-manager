package com.example.flowmanager.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStatusResponse {
    private Long id;
    private String status;

    private String originalBucket;
    private String originalKey;

    private String resultBucket;
    private String resultKey;

    private String error;
}