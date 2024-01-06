package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountResponse;
import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImp implements AccountService {

    AccountRepository accountRepository;

    ModelMapper modelMapper;

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
        log.info("The account has been saved with name {}", account.getUsername());
    }

    @Override
    public void uploadProfilePicture(String pictureLink) {
        Account account = fetchAccount();
        account.setProfilePicture(pictureLink);
        accountRepository.save(account);
    }

    @Override
    public AccountResponse fetchAccountResponse() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return convertToAccountResponse(accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Account not founded!")));
    }

    @Override
    public Account fetchAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Account not founded!"));
    }

    private AccountResponse convertToAccountResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
