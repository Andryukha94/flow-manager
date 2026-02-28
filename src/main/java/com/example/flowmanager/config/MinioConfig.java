package com.example.flowmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class MinioConfig {

    @Bean
    public S3Client s3Client(
            @Value("${app.s3.endpoint}") String endpoint,
            @Value("${app.s3.accessKey}") String accessKey,
            @Value("${app.s3.secretKey}") String secretKey,
            @Value("${app.s3.region}") String region,
            @Value("${app.s3.forcePathStyle:true}") boolean forcePathStyle
    ) {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(forcePathStyle).build())
                .build();
    }
}