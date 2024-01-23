package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.api.util.exception.IncorrectPasswordException;
import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;
import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountCredentialsServiceImp implements AccountCredentialsService {

    AccountRetrievalService accountRetrievalService;
    AccountExistenceCheckService accountExistenceCheckService;

    PasswordEncoder passwordEncoder;

    AccountRepository accountRepository;

    @Override
    public void updatePassword(AccountUpdatePassword accountUpdatePassword) {
        Account account = accountRetrievalService.fetchAccount();

        if (comparePassword(accountUpdatePassword)) {
            account.setPassword(passwordEncoder.encode(accountUpdatePassword.getNewPassword()));

            accountRepository.save(account);
        } else {
            throw new IncorrectPasswordException("Incorrect current password!");
        }
    }

    @Override
    public void updateUsername(String username) {
/*        if (accountRetrievalService.doesAccountExistByUsername(username))
            return "This username is already taken!";*/
        Account account = accountRetrievalService.fetchAccount();
        account.setUsername(username);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void changeEmail(String email) {
        Account account = accountRetrievalService.fetchAccount();

        account.setIsVerifiedEmail(false);
        account.setEmail(email);

        accountRepository.save(account);
    }

    private boolean comparePassword(AccountUpdatePassword account) {
        return passwordEncoder.matches(account.getCurrentPassword(), accountRetrievalService.fetchAccount().getPassword());
    }
}
