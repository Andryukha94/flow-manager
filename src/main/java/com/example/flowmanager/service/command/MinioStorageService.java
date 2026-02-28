package com.example.flowmanager.service.command;

import com.example.flowmanager.exception.FileNotFoundException;
import com.example.flowmanager.exception.MinioUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
@RequiredArgsConstructor
public class MinioStorageService {

    private final S3Client s3;

    @Value("${app.storage.incomingBucket}")
    private String incomingBucket;

    @Value("${app.storage.outgoingBucket}")
    private String outgoingBucket;

    public String getIncomingBucket() {
        return incomingBucket;
    }

    public String getOutgoingBucket() {
        return outgoingBucket;
    }

    public void uploadIncoming(String objectKey, byte[] bytes, String contentType) {
        try {
            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(incomingBucket)
                            .key(objectKey)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromBytes(bytes)
            );

        } catch (S3Exception e) {
            throw new MinioUnavailableException(e);

        } catch (Exception e) {
            throw new MinioUnavailableException(e);
        }
    }

    public byte[] downloadOutgoing(String objectKey) {
        try {
            ResponseBytes<GetObjectResponse> bytes = s3.getObjectAsBytes(
                    GetObjectRequest.builder()
                            .bucket(outgoingBucket)
                            .key(objectKey)
                            .build()
            );
            return bytes.asByteArray();

        } catch (NoSuchKeyException e) {
            throw new FileNotFoundException(e);

        } catch (S3Exception e) {
            if (e.statusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new FileNotFoundException(e);
            }
            throw new MinioUnavailableException(e);

        } catch (Exception e) {
            throw new MinioUnavailableException(e);
        }
    }
}