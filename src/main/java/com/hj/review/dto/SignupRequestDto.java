package com.hj.review.dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String provider;
    private String providerId;
    private String nickname;
    private String email;
}