package com.example.flowmanager.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public final class FileUtils {
    private FileUtils() {}

    public static byte[] toBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String contentTypeOrDefault(MultipartFile file) {
        String ct = file.getContentType();
        return (ct == null || ct.isBlank()) ? "application/octet-stream" : ct;
    }
}
