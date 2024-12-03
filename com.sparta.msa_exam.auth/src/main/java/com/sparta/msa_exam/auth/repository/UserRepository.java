package com.sparta.msa_exam.auth.repository;

import com.sparta.msa_exam.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);  // 사용자 이름으로 사용자 찾기
    boolean existsByUsername(String username);  // 사용자 이름이 이미 존재하는지 확인
}
