package dev.lebenkov.messenger.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CompressService {
    File compressFile(File tempFile);

    File createTempFile(MultipartFile file);
}
