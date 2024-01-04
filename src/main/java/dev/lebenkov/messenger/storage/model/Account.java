package dev.lebenkov.messenger.storage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "is_verified_email")
    private Boolean isVerifiedEmail;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}