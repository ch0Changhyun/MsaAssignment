package com.sparta.msa_exam.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "Username은 4자 이상, 10자 이하여야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "Password는 8자 이상, 15자 이하여야 합니다.")
    private String password;
}
