package dev.lebenkov.messenger.storage.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUpdatePassword {

    private String currentPassword;

    @Size(min = 6, max = 15, message = "Password should be 6 - 15 size!")
    @NotNull(message = "Password should not be empty!")
    private String newPassword;
}
