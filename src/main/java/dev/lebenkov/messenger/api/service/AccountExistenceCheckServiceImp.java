package dev.lebenkov.messenger.api.service;

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
public class AccountExistenceCheckServiceImp implements AccountExistenceCheckService {

    AccountRepository accountRepository;

    @Override
    public boolean doesAccountExistByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean doesEmailUsed(String email) {
        return accountRepository.findAccountByEmail(email).isPresent();
    }
}
