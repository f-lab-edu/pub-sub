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

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        validateSignUp(signUpRequest);

        User user = User.builder()
            .nickname(signUpRequest.nickname())
            .name(signUpRequest.name())
            .email(signUpRequest.email())
            .password(passwordEncoder.encode(signUpRequest.password()))
            .bio(signUpRequest.bio())
            .role(USER)
            .build();

        userRepository.save(user);
    }

    /**
     * 닉네임, 이메일에 대한 중복 검사를 수행합니다.
     */
    private void validateSignUp(SignUpRequest signUpRequest) {
        if (userRepository.findByNickname(signUpRequest.nickname()).isPresent()) {
            throw new DuplicateValueException("중복되는 닉네임입니다.");
        }

        if (userRepository.findByEmail(signUpRequest.email()).isPresent()) {
            throw new DuplicateValueException("중복되는 이메일입니다.");
        }
    }
}
