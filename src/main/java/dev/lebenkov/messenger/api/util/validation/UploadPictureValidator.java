package dev.lebenkov.messenger.api.util.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class UploadPictureValidator implements Validator {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile file = (MultipartFile) target;

        log.info("in validator");
        if (file.isEmpty()) {
            errors.rejectValue("file", "", "Please select a file to upload.");
            log.info("error1");
            return;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            log.info("error2");
            errors.rejectValue("file", "", "File size exceeds the maximum allowed limit (5MB).");
        }
    }
}
