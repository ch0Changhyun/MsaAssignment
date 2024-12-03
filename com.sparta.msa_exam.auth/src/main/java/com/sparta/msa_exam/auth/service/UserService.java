package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.dto.SignInRequestDto;
import com.sparta.msa_exam.auth.dto.SignUpRequestDto;
import com.sparta.msa_exam.auth.entity.User;
import com.sparta.msa_exam.auth.jwt.JwtProvider;
import com.sparta.msa_exam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void registerUser(SignUpRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getUsername(), encodedPassword);
        userRepository.save(user);
    }

    public String authenticateUser(SignInRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.generateToken(user.getUsername());
    }
}
