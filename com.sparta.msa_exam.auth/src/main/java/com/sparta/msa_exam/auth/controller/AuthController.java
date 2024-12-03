package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.dto.TokenResponseDto;
import com.sparta.msa_exam.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto requestDto) {
        userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody SignInRequestDto request) {
        String token = userService.authenticateUser(request);
        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}

