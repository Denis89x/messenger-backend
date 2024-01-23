package dev.lebenkov.messenger.storage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountUpdateEmail {

    @Email
    @NotNull
    private String email;
}
