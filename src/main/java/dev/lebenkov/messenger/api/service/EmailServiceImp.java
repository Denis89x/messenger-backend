package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.model.Account;
import dev.lebenkov.messenger.storage.repository.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {

    private final JavaMailSender mailSender;

    private final AccountRepository accountRepository;

    private final EmailCodeGenerator emailCodeGenerator;
    private final AccountRetrievalService accountRetrievalService;

    private final Map<Long, String> verificationCodes = new HashMap<>();

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmailCode() {
        Account account = accountRetrievalService.fetchAccount();

        String code = emailCodeGenerator.generateVerificationCode();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(account.getEmail());
            helper.setFrom(email);
            helper.setSubject("Verification Code for Your Account");
            helper.setText("Your verification code is: " + code);

            mailSender.send(message);
            verificationCodes.put(account.getId(), code);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке письма на адрес: {}", account.getEmail());
        }
    }

    @Override
    public void verifyEmail(String codeRequest) {
        Account account = accountRetrievalService.fetchAccount();

        String code = verificationCodes.get(account.getId());

        if (codeRequest.equals(code)) {
            account.setIsVerifiedEmail(true);
            accountRepository.save(account);
        }
    }

    @Override
    public void unlinkEmail() {
        Account account = accountRetrievalService.fetchAccount();
        account.setIsVerifiedEmail(false);
        accountRepository.save(account);
    }
}