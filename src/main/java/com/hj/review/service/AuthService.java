package com.hj.review.service;

import com.hj.review.domain.User;
import com.hj.review.dto.SignupRequestDto;
import com.hj.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String signup(SignupRequestDto request) {
        String provider = request.getProvider();
        String providerId = request.getProviderId();
        String email = request.getEmail();
        String nickname = request.getNickname();

        Optional<User> existingUser = userRepository.findByProviderAndProviderId(provider, providerId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.updateProfile(nickname, email);
            userRepository.save(user);
        } else {
            User user = new User(null, provider, providerId, nickname, email);
            userRepository.save(user);
        }

        return "회원가입 완료";
    }
}
