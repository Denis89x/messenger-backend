package dev.lebenkov.messenger.api.util.validation;

import dev.lebenkov.messenger.api.service.AccountDetailsService;
import dev.lebenkov.messenger.storage.dto.AccountRequestLogin;
import dev.lebenkov.messenger.storage.dto.AccountRequestRegistration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountValidator implements Validator {

    AccountDetailsService accountDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRequestRegistration.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRequestRegistration person = (AccountRequestRegistration) target;

        try {
            accountDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "This account already exists!");
    }
}
