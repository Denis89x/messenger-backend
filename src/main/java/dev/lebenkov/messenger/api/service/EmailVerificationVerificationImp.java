package dev.lebenkov.messenger.api.service;

import dev.lebenkov.messenger.storage.model.Account;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationVerificationImp implements EmailVerification {

    private final JavaMailSender mailSender;

    private final EmailCodeGenerator emailCodeGenerator;
    private final AccountRetrievalService accountRetrievalService;

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

            log.info("Code: {}, email: {}", code, email);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке письма на адрес: {}", account.getEmail());
        }
    }
}
