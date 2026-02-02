package com.example.flowmanager.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private Long id;
    private String status;
}
