package dev.lebenkov.messenger.api.service;

public interface AccountModificationService {
    String updateUsername(String username);
    void updateFirstName(String firstName);
    void updateLastName(String lastName);
    void updateProfilePicture(String pictureLink);
}
