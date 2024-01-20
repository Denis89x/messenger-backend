package dev.lebenkov.messenger.api.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class EmailCodeGenerator {

    private static final int MIN_CODE = 100000;
    private static final int MAX_CODE = 999999;

    public String generateVerificationCode() {
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(MAX_CODE - MIN_CODE + 1) + MIN_CODE;
        return String.valueOf(code);
    }
}
