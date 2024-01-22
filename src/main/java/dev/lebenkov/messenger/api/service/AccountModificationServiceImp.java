package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.api.util.exception.IncorrectPasswordException;
import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;
import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountModificationServiceImp implements AccountModificationService {

    AccountRetrievalService accountRetrievalService;
    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public String updateUsername(String username) {
        if (accountRetrievalService.doesAccountExistByUsername(username))
            return "This username is already taken!";

        Account account = accountRetrievalService.fetchAccount();
        account.setUsername(username);
        accountRepository.save(account);

        updateAuthenticationUsername(username);
        return "Username changed successfully!";
    }

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

    @Override
    public void updateProfilePicture(String pictureLink) {
        Account account = accountRetrievalService.fetchAccount();

        account.setProfilePicture(pictureLink);
        accountRepository.save(account);

        log.info("The image has been updated from id: {}", account.getId());
    }

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

    private boolean comparePassword(AccountUpdatePassword account) {
        return passwordEncoder.matches(account.getCurrentPassword(), accountRetrievalService.fetchAccount().getPassword());
    }

    private void updateAuthenticationUsername(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                username, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
    }
}
