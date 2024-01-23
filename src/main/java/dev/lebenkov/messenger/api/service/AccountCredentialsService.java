package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;

public interface AccountCredentialsService {
    void updatePassword(AccountUpdatePassword accountUpdatePassword);
    void updateUsername(String username);
    void changeEmail(String email);
}
