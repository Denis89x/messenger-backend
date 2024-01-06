package dev.lebenkov.messenger.api.service;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ContentSource;
import com.backblaze.b2.client.contentSources.B2ContentTypes;
import com.backblaze.b2.client.contentSources.B2FileContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsService implements PictureService {

    private final B2StorageServiceImp b2StorageServiceImp;
    private final CompressService compressService;

    @Value("${bucket.id}")
    private String bucketId;

    @Override
    public String uploadProfilePicture(MultipartFile file) {
        B2StorageClient client = b2StorageServiceImp.getClient();

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        try {
            String fileName = UUID.randomUUID() + extension;

            File tempFile = compressService.createTempFile(file);
            File compressedFile = compressService.compressFile(tempFile);

            B2ContentSource source = B2FileContentSource.build(compressedFile);

            B2UploadFileRequest request = B2UploadFileRequest.
                    builder(bucketId, fileName, B2ContentTypes.B2_AUTO, source)
                    .setCustomField("color", "green")
                    .build();

            B2FileVersion fileVersion = client.uploadSmallFile(request);

            tempFile.delete();
            compressedFile.delete();

            return "https://f005.backblazeb2.com/file/lebenkovMessenger/" + fileVersion.getFileName();
        } catch (B2Exception e) {
            log.error("Ошибка при загрузке фотографии", e);
            throw new RuntimeException("Ошибка при загрузке фотографии!", e);
        }
    }
}