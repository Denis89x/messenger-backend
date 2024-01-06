package dev.lebenkov.messenger.api.controller;

import dev.lebenkov.messenger.api.service.AccountService;
import dev.lebenkov.messenger.api.service.AccountServiceImp;
import dev.lebenkov.messenger.api.service.PictureService;
import dev.lebenkov.messenger.api.util.validation.UploadPictureValidator;
import dev.lebenkov.messenger.storage.dto.AccountResponse;
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

    AccountService accountService;
    PictureService pictureService;

    UploadPictureValidator uploadPictureValidator;

    @GetMapping
    public ResponseEntity<AccountResponse> fetchAccount() {
        return new ResponseEntity<>(accountService.fetchAccountResponse(), HttpStatus.OK);
    }

    @PostMapping("/upload-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @RequestParam("picture") MultipartFile picture) {

/*        uploadPictureValidator.validate(picture, bindingResult);

        log.info("Here");
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getFieldError().toString(), HttpStatus.BAD_REQUEST);*/

        accountService.uploadProfilePicture(pictureService.uploadProfilePicture(picture));
        return new ResponseEntity<>("Profile picture successfully uploaded!", HttpStatus.OK);
    }
}
