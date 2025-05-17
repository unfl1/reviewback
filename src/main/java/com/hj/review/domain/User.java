package com.hj.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;     // ex) kakao
    private String providerId;   // 카카오에서 받은 유저 ID

    private String nickname;     // 사용자 닉네임
    private String email;        // 사용자 이메일

    // 추가 정보 수정 메서드
    public void updateProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}