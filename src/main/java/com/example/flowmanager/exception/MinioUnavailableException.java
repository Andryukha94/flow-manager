package com.example.flowmanager.exception;

public class MinioUnavailableException extends RuntimeException {
    public MinioUnavailableException() {
        super();
    }
    public MinioUnavailableException(Throwable cause) {
        super(cause);
    }
}
