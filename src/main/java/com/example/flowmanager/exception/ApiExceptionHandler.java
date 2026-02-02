package com.example.flowmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void badRequest() {}

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void notFound() {}

    @ExceptionHandler(FileProcessingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    void conflict() {}

    @ExceptionHandler(MinioUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    void minioUnavailable() {}
}