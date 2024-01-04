package dev.lebenkov.messenger.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestRegistration {

    private String username;

    private String password;

    private String email;
}