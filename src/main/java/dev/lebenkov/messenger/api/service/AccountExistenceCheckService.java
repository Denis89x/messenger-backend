package dev.lebenkov.messenger.api.service;

public interface AccountExistenceCheckService {
    boolean doesAccountExistByUsername(String username);
    boolean doesEmailUsed(String email);
}
