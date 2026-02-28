package com.example.flowmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_bucket", nullable = false)
    private String originalBucket;

    @Column(name = "original_key", nullable = false, length = 1024)
    private String originalKey;

    @Column(name = "result_bucket")
    private String resultBucket;

    @Column(name = "result_key", length = 1024)
    private String resultKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private FileStatus status;

    @Column(name = "error")
    private String error;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}