package dev.lebenkov.messenger.api.controller;

import dev.lebenkov.messenger.api.service.AuthService;
import dev.lebenkov.messenger.api.util.validation.AccountValidator;
import dev.lebenkov.messenger.storage.dto.AccountRequestLogin;
import dev.lebenkov.messenger.storage.dto.AccountRequestRegistration;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
public class AuthController {

    AccountValidator accountValidator;
    AuthService authService;

    @PostMapping("/register")
    public Map<String, String> performRegistration(
            @RequestBody @Valid AccountRequestRegistration accountRequestRegistration,
            BindingResult bindingResult) {
        accountValidator.validate(accountRequestRegistration, bindingResult);

        if (bindingResult.hasErrors())
            return Map.of("Error", bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .filter(Objects::nonNull)
                    .toList()
                    .toString());

        return authService.register(accountRequestRegistration) ;
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(
            @RequestBody @Valid AccountRequestLogin accountRequestLogin) {
        return authService.login(accountRequestLogin);
    }
}
