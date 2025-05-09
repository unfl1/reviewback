package com.hj.review.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // OAuth2UserService에서 저장한 principal 정보 가져오기
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String userId = String.valueOf(oAuth2User.getUser().getId());

        // JWT 발급
        String token = jwtProvider.createToken(userId);

        // 유저가 이미 존재하는 경우: JWT 발급 후 홈으로 이동
        if (oAuth2User.getUser().getNickname() != null) {
            String redirectUri = "http://localhost:3000/oauth2/redirect?token=" +
                    URLEncoder.encode(token, StandardCharsets.UTF_8);
            response.sendRedirect(redirectUri);
        } else {
            // 유저가 없는 경우: 추가 정보 입력 페이지로 이동
            String provider = oAuth2User.getUser().getProvider();
            String providerId = oAuth2User.getUser().getProviderId();
            String redirectUri = "http://localhost:3000/signup?provider=" + provider +
                    "&providerId=" + providerId;

            response.sendRedirect(redirectUri);
        }
    }
}