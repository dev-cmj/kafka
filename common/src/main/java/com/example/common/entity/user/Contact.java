package com.example.common.entity.user;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Embeddable
@Getter
public class Contact {
    @Email
    private String email;

    @Pattern(regexp = "^\\d{10,11}$")
    private String phone;

    protected Contact() {}

    public Contact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}