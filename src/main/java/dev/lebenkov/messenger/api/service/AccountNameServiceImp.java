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
public class AccountNameServiceImp implements AccountNameService {

    AccountRetrievalService accountRetrievalService;

    AccountRepository accountRepository;

    @Override
    public void updateFirstName(String firstName) {
        Account account = accountRetrievalService.fetchAccount();

        account.setFirstName(firstName);
        accountRepository.save(account);
    }

    @Override
    public void updateLastName(String lastName) {
        Account account = accountRetrievalService.fetchAccount();

        account.setLastName(lastName);
        accountRepository.save(account);
    }
}
