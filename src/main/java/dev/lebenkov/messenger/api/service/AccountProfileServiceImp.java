package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountProfileServiceImp implements AccountProfileService {

    AccountRetrievalService accountRetrievalService;

    AccountRepository accountRepository;

    @Override
    public void updateProfilePicture(String pictureLink) {
        Account account = accountRetrievalService.fetchAccount();

        account.setProfilePicture(pictureLink);
        accountRepository.save(account);
    }
}
