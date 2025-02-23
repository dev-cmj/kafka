package com.example.common.entity.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;

@Embeddable
@Getter
public class UserStatus {
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime lastStatusChange;

    public static final UserStatus ACTIVE = new UserStatus(Status.ACTIVE);
    public static final UserStatus INACTIVE = new UserStatus(Status.INACTIVE);

    protected UserStatus() {}

    private UserStatus(Status status) {
        this.status = status;
        this.lastStatusChange = LocalDateTime.now();
    }

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
