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
        return new User(
                null,               // id (null로 설정)
                provider,
                providerId,
                null,       // nickname
                null                // email
        );
    }
}