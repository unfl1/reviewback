package com.hj.review.service;

import com.hj.review.domain.User;
import com.hj.review.dto.UserDto;
import com.hj.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.hj.review.security.CustomOAuth2User;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 카카오 유저 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // provider 정보 (ex: kakao)
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // providerId만 사용
        String providerId = String.valueOf(attributes.get("id"));

        // DTO 생성 (email/nickname 생략)
        UserDto userDto = UserDto.builder()
                .provider(provider)
                .providerId(providerId)
                .build();

        // DB 조회 또는 저장
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> userRepository.save(userDto.toEntity()));

        // 커스텀 OAuth2User 리턴
        return new CustomOAuth2User(user, attributes);
    }
}