package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountResponse;
import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImp implements AccountService{

    AccountModificationService accountModificationService;
    AccountRetrievalService accountRetrievalService;

    @Override
    public AccountResponse fetchAccountResponse() {
        return accountRetrievalService.fetchAccountResponse();
    }

    @Override
    public String updateUsername(String username) {
        return accountModificationService.updateUsername(username);
    }

    @Override
    public void updateFirstName(String firstName) {
        accountModificationService.updateFirstName(firstName);
    }

    @Override
    public void updateProfilePicture(String pictureLink) {
        accountModificationService.updateProfilePicture(pictureLink);
    }

    @Override
    public void updateLastName(String lastName) {
        accountModificationService.updateLastName(lastName);
    }

    @Override
    public void updatePassword(AccountUpdatePassword accountUpdatePassword) {
        accountModificationService.updatePassword(accountUpdatePassword);
    }
}