package com.stemm.pubsub.service.auth.token;

import com.stemm.pubsub.service.auth.userdetails.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.stemm.pubsub.service.user.entity.Role.USER;

@Slf4j
@RequiredArgsConstructor
public class TokenProcessingFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/signup", "/login", "/docs/**"};

    private final TokenService tokenService;
    private final UserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isWhitelist(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> accessToken = tokenService.extractAccessToken(request);
        Optional<String> refreshToken = tokenService.extractRefreshToken(request);

        // access token 유효한 경우
        if (accessToken.isPresent() && isValidToken(accessToken.get())) {
            Optional<String> nickname = extractNickname(accessToken.get());
            nickname.ifPresent(this::authenticateUser);
            filterChain.doFilter(request, response);
            return;
        }

        // refresh token 있는 경우
        // FIXME: refresh token은 있지만 유효하지 않은 경우(e.g. 만료, 위변조)도 포함하기 때문에 위변조된 경우 보안에 취약할 수 있습니다.
        if (refreshToken.isPresent()) {
            Optional<Long> userId = extractUserId(refreshToken.get());
            Optional<String> nickname = extractNickname(refreshToken.get());

            if (userId.isPresent() && nickname.isPresent()) {
                reissueTokens(response, userId.get(), nickname.get());
            }

            return;
        }

        // 토큰 존재하지 않으면 로그인 페이지로 redirect
        redirectToLogin(response);
    }

    private boolean isWhitelist(String requestUri) {
        return PatternMatchUtils.simpleMatch(whitelist, requestUri);
    }

    private boolean isValidToken(String token) {
        return tokenService.isValidToken(token);
    }

    private Optional<Long> extractUserId(String token) {
        return tokenService.extractUserId(token);
    }

    private Optional<String> extractNickname(String token) {
        return tokenService.extractNickname(token);
    }

    /**
     * 인증된 유저를 security context에 저장합니다.
     */
    private void authenticateUser(String nickname) {
        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(nickname);

        SecurityContextHolder.getContext().setAuthentication(
            UsernamePasswordAuthenticationToken.authenticated(
                customUserDetails,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(USER.getKey()))
            )
        );
    }

    private String reissueAccessToken(Long userId, String nickname) {
        return tokenService.createAccessToken(userId, nickname);
    }

    private String reissueRefreshToken(Long userId, String nickname) {
        return tokenService.createRefreshToken(userId, nickname);
    }

    /**
     * 클라이언트에게 access token과 refresh token을 RTR 방식으로 재발급합니다.
     */
    private void reissueTokens(HttpServletResponse response, Long userId, String nickname) {
        String reissuedAccessToken = reissueAccessToken(userId, nickname);
        String reissuedRefreshToken = reissueRefreshToken(userId, nickname);
        tokenService.sendAccessTokenAndRefreshToken(response, reissuedAccessToken, reissuedRefreshToken);
        tokenService.updateRefreshToken(userId, reissuedRefreshToken);
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }
}
