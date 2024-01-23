package dev.lebenkov.messenger.api.util.validation;

import dev.lebenkov.messenger.api.service.AccountExistenceCheckService;
import dev.lebenkov.messenger.storage.dto.AccountUpdateEmail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailValidator implements Validator {

    AccountExistenceCheckService accountExistenceCheckService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountUpdateEmail.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountUpdateEmail accountUpdateEmail = (AccountUpdateEmail) target;

        if (accountExistenceCheckService.doesEmailUsed(accountUpdateEmail.getEmail())) {
            errors.rejectValue("email", "", "This email already used!");
        }
    }
}
