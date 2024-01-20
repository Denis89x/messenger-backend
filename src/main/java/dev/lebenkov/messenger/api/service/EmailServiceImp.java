package dev.lebenkov.messenger.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImp implements EmailService {

    EmailVerification emailVerification;

    @Override
    public void sendEmailCode() {
        emailVerification.sendEmailCode();
    }
}
