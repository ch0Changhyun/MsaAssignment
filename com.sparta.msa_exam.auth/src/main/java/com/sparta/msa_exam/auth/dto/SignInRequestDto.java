package com.sparta.msa_exam.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
