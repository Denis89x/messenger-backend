package dev.lebenkov.messenger.api.service;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class B2StorageServiceImp implements B2StorageService {

    @Getter
    private B2StorageClient client;

    @Getter
    @Value("${application.key.id}")
    private String applicationKeyId;

    @Value("${application.key.value}")
    private String applicationKey;

    @Override
    @PostConstruct
    public void init() {
        try {
            client = B2StorageClientFactory
                    .createDefaultFactory()
                    .create(applicationKeyId, applicationKey, "messenger-backend/1.0");
        } catch (Exception e) {
            log.error("Failed to initialize B2StorageService: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize B2StorageService", e);
        }
    }
}