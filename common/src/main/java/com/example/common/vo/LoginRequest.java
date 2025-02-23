package com.example.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "사용자 ID는 필수입니다")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
