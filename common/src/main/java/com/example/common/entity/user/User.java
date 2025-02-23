package com.example.common.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Embedded
    private UserStatus status = UserStatus.ACTIVE;

    // Value Object로 분리할 수 있는 객체들
    @Embedded
    private Contact contact;

    public void updateContact(String email, String phone) {
        this.contact = new Contact(email, phone);
    }


    public void validatePassword(String rawPassword,
                                  String encryptedPassword,
                                  PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
            throw new IllegalArgumentException("Password is invalid.");
        }
    }
}


