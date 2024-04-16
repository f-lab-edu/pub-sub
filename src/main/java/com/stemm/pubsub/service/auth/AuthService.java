package com.stemm.pubsub.service.auth;

import com.stemm.pubsub.service.auth.dto.SignUpRequest;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stemm.pubsub.service.user.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // TODO: 리턴값?? custom exception 정의 및 메서드 분리
    // TODO: 중복 체크 매번 db 접근??
    @Transactional
    public User signUp(SignUpRequest signUpRequest) throws Exception {
        if (userRepository.findByNickname(signUpRequest.nickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
            .nickname(signUpRequest.nickname())
            .name(signUpRequest.name())
            .email(signUpRequest.email())
            .password(passwordEncoder.encode(signUpRequest.password()))
            .bio(signUpRequest.bio())
            .role(USER)
            .build();

        return userRepository.save(user);
    }
}
