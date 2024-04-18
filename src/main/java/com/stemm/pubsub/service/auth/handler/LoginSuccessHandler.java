package com.stemm.pubsub.service.auth.handler;

import com.stemm.pubsub.service.auth.token.TokenService;
import com.stemm.pubsub.service.auth.userdetails.CustomUserDetails;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        String nickname = extractNickname(authentication);
        String accessToken = tokenService.createAccessToken(userId, nickname);
        String refreshToken = tokenService.createRefreshToken(userId, nickname);

        tokenService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

        userRepository.findById(userId)
            .ifPresent(user -> {
                user.updateRefreshToken(refreshToken);
                userRepository.saveAndFlush(user);
            });

        log.info("로그인에 성공하였습니다. user id: {}", userId);
    }

    private Long extractUserId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    private String extractNickname(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
