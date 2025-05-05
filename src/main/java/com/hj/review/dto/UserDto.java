package com.hj.review.dto;

import com.hj.review.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private String provider;
    private String providerId;

    public User toEntity() {
        return User.builder()
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}