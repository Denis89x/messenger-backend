package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.api.security.AccountDetails;
import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountDetailsService implements UserDetailsService {

    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new AccountDetails(account.get());
    }
}
