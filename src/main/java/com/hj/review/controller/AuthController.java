package com.hj.review.controller;

import com.hj.review.domain.User;
import com.hj.review.dto.SignupRequestDto;
import com.hj.review.repository.UserRepository;
import com.hj.review.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")  // 기본 경로를 /user로 설정
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto request) {
        try {
            // OAuth 요청 처리 로직
            String provider = request.getProvider();
            String providerId = request.getProviderId();
            String email = request.getEmail();
            String nickname = request.getNickname();

            // 유저 정보 저장
            User user = User.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .email(email)
                    .nickname(nickname)
                    .build();
            userRepository.save(user);

            return ResponseEntity.ok("회원가입 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 오류: " + e.getMessage());
        }
    }
}