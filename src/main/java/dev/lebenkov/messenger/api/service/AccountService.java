package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountModificationService accountModificationService;
    AccountRetrievalService accountRetrievalService;

    public AccountResponse fetchAccountResponse() {
        return accountRetrievalService.fetchAccountResponse();
    }

    public String updateUsername(String username) {
        return accountModificationService.updateUsername(username);
    }

    public void updateFirstName(String firstName) {
        accountModificationService.updateFirstName(firstName);
    }

    public void updateProfilePicture(String pictureLink) {
        accountModificationService.updateProfilePicture(pictureLink);
    }

    public void updateLastName(String lastName) {
        accountModificationService.updateLastName(lastName);
    }
}