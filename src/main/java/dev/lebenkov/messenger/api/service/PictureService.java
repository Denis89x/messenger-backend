package dev.lebenkov.messenger.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    String uploadProfilePicture(MultipartFile file);
}
