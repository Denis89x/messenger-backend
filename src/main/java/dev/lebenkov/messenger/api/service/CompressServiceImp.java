package dev.lebenkov.messenger.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompressServiceImp implements CompressService {

    @Override
    public File compressFile(File tempFile) {
        try {
            File compressedFile = File.createTempFile("compressed-", ".tmp");

            Thumbnails.of(tempFile)
                    .size(200, 200)
                    .outputFormat("png")
                    .toOutputStream(new FileOutputStream(compressedFile));

            log.info("file1 {}", compressedFile.getAbsoluteFile());
            return compressedFile;
        } catch (IOException e) {
            log.error("Ошибка при сжатии фотографии", e);
        }
        return null;
    }

    @Override
    public File createTempFile(MultipartFile file) {
        try {
            Path tempFilePath = Files.createTempFile("temp-", null);
            Files.write(tempFilePath, file.getBytes());
            return tempFilePath.toFile();
        } catch (IOException e) {
            log.error("Ошибка при создании временного файла", e);
            return null;
        }
    }
}