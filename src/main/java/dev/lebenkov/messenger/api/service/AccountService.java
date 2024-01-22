package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountResponse;
import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;

public interface AccountService {
    AccountResponse fetchAccountResponse();

    String updateUsername(String username);

    void updateFirstName(String firstName);

    void updateProfilePicture(String pictureLink);

    void updateLastName(String lastName);

    void updatePassword(AccountUpdatePassword accountUpdatePassword);
}
