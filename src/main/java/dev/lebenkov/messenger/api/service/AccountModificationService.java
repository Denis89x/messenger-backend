package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;

public interface AccountModificationService {
    String updateUsername(String username);
    void updateFirstName(String firstName);
    void updateLastName(String lastName);
    void updateProfilePicture(String pictureLink);
    void updatePassword(AccountUpdatePassword accountUpdatePassword);
}
