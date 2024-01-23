package dev.lebenkov.messenger.api.controller;

import dev.lebenkov.messenger.api.service.*;
import dev.lebenkov.messenger.api.util.exception.IncorrectPasswordException;
import dev.lebenkov.messenger.api.util.validation.EmailValidator;
import dev.lebenkov.messenger.storage.dto.AccountResponse;
import dev.lebenkov.messenger.storage.dto.AccountUpdateEmail;
import dev.lebenkov.messenger.storage.dto.AccountUpdatePassword;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsController {

    AccountCredentialsService accountCredentialsService;
    AccountRetrievalService accountRetrievalService;
    AccountProfileService accountProfileService;
    AccountNameService accountNameService;
    PictureService pictureService;
    EmailService emailService;

    EmailValidator emailValidator;

    @GetMapping
    public ResponseEntity<AccountResponse> fetchAccount() {
        return new ResponseEntity<>(accountRetrievalService.fetchAccountResponse(), HttpStatus.OK);
    }

    @PostMapping("/upload-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @RequestParam("picture") MultipartFile picture) {

        accountProfileService.updateProfilePicture(pictureService.uploadProfilePicture(picture));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/username/{username}")
    public ResponseEntity<String> updateUsername(@PathVariable String username) {
        accountCredentialsService.updateUsername(username);
        return new ResponseEntity<>("Username was changed successfully!", HttpStatus.OK);
    }

    @PatchMapping("/firstname/{firstname}")
    public ResponseEntity<String> updateFirstName(@PathVariable("firstname")
                                                  @Size(min = 3, max = 15, message = "Длина имени должна быть от 3 до 15 символов")
                                                  @Pattern(regexp = "^[a-zA-Z]+$", message = "Имя может содержать только буквы")
                                                  String firstName) {
        accountNameService.updateFirstName(firstName);
        return new ResponseEntity<>("Firstname changed successfully!", HttpStatus.OK);
    }

    @PatchMapping("/lastname/{lastname}")
    public ResponseEntity<String> updateSecondName(@PathVariable("lastname")
                                                   @Size(min = 3, max = 20, message = "Длина фамилия должна быть от 3 до 20 символов")
                                                   @Pattern(regexp = "^[a-zA-Z]+$", message = "Фамилия может содержать только буквы")
                                                   String lastName) {
        accountNameService.updateLastName(lastName);
        return new ResponseEntity<>("Lastname changed successfully!", HttpStatus.OK);
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> updateEmail(@RequestBody AccountUpdateEmail accountUpdateEmail, BindingResult bindingResult) {
        emailValidator.validate(accountUpdateEmail, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        accountCredentialsService.changeEmail(accountUpdateEmail.getEmail());
        return new ResponseEntity<>("Email was successfully changed!", HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid AccountUpdatePassword accountUpdatePassword) {
        try {
            accountCredentialsService.updatePassword(accountUpdatePassword);
            return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            return new ResponseEntity<>("Incorrect current password!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send-email-code")
    public ResponseEntity<String> sendEmailCode() {
        emailService.sendEmailCode();
        return new ResponseEntity<>("Email code was successfully sent!", HttpStatus.OK);
    }

    @PostMapping("/verify-email/{code}")
    public ResponseEntity<String> verifyEmail(@PathVariable String code) {
        emailService.verifyEmail(code);
        return new ResponseEntity<>("Email was successfully verified!", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> unlinkEmail() {
        emailService.unlinkEmail();
        return new ResponseEntity<>("Email was unlinked", HttpStatus.OK);
    }
}