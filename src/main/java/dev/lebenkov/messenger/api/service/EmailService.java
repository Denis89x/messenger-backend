package dev.lebenkov.messenger.api.service;

public interface EmailService {
    void sendEmailCode();
    void verifyEmail(String codeRequest);
    void unlinkEmail();
}
