package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.dto.AccountRequestLogin;
import dev.lebenkov.messenger.storage.dto.AccountRequestRegistration;

import java.util.Map;

public interface AuthService {
    Map<String, String> register(AccountRequestRegistration accountRequestRegistration);
    Map<String, String> login(AccountRequestLogin accountRequestLogin);
}