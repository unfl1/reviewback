package com.hj.review.controller;

import com.hj.review.dto.SignupRequestDto;
import com.hj.review.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto request) {
        String message = authService.signup(request);
        return ResponseEntity.ok(message);
    }
}
