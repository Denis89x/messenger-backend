package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountResponse;
import dev.lebenkov.messenger.storage.model.Account;

public interface AccountRetrievalService {
    AccountResponse fetchAccountResponse();
    Account fetchAccount();
    boolean doesAccountExistByUsername(String username);
}
