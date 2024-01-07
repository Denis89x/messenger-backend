package dev.lebenkov.messenger.api.controller;

import dev.lebenkov.messenger.api.service.AccountService;
import dev.lebenkov.messenger.api.service.PictureService;
import dev.lebenkov.messenger.storage.dto.AccountResponse;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsController {

    AccountService accountService;
    PictureService pictureService;

    @GetMapping
    public ResponseEntity<AccountResponse> fetchAccount() {
        return new ResponseEntity<>(accountService.fetchAccountResponse(), HttpStatus.OK);
    }

    @PostMapping("/upload-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @RequestParam("picture") MultipartFile picture) {

        accountService.updateProfilePicture(pictureService.uploadProfilePicture(picture));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/username/{username}")
    public ResponseEntity<String> updateUsername(@PathVariable Optional<String> username) {
        return new ResponseEntity<>(accountService.updateUsername(username.orElseThrow(() ->
                new RuntimeException("Username is null!"))), HttpStatus.OK);
    }

    @PatchMapping("/firstname/{firstname}")
    public ResponseEntity<String> updateFirstName(@PathVariable("firstname")
                                                  @Size(min = 3, max = 15, message = "Длина имени должна быть от 3 до 15 символов")
                                                  @Pattern(regexp = "^[a-zA-Z]+$", message = "Имя может содержать только буквы")
                                                  String firstName) {
        accountService.updateFirstName(firstName);
        return new ResponseEntity<>("Firstname changed successfully!", HttpStatus.OK);
    }

    @PatchMapping("/lastname/{lastname}")
    public ResponseEntity<String> updateSecondName(@PathVariable("lastname")
                                                   @Size(min = 3, max = 20, message = "Длина фамилия должна быть от 3 до 20 символов")
                                                   @Pattern(regexp = "^[a-zA-Z]+$", message = "Фамилия может содержать только буквы")
                                                   String lastName) {
        accountService.updateLastName(lastName);
        return new ResponseEntity<>("Lastname changed successfully!", HttpStatus.OK);
    }
}
