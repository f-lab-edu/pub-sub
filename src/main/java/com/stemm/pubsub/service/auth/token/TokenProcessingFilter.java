package com.stemm.pubsub.service.auth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.stemm.pubsub.service.user.entity.Role.USER;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
public class TokenProcessingFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/signup", "/login", "/logout"};

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (isWhitelist(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> accessToken = tokenService.extractAccessToken(request);
        Optional<String> refreshToken = tokenService.extractRefreshToken(request);

        // access token 유효한 경우
        if (accessToken.isPresent() && isValidToken(accessToken.get())) {
            Optional<Long> userId = extractUserId(accessToken.get());
            userId.ifPresent(id -> authenticateUser(id));
            filterChain.doFilter(request, response);
        }

        // refresh token 있는 경우
        // refresh token은 있지만 유효하지 않은 경우(e.g. 만료, 위변조)도 포함하기 때문에 위변조된 경우 보안에 취약할 수 있습니다.
        if (refreshToken.isPresent()) {
            Optional<Long> userId = extractUserId(refreshToken.get());
            userId.ifPresent(id -> reissueTokens(response, id));
            return;
        }

        // TODO: 어느 필터에서 request reject 해야하나? (일단 여기서 함)
        rejectRequest(response);
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

    /**
     * 인증된 유저를 security context에 저장합니다.
     */
    private void authenticateUser(Long userId) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userId,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(USER.getKey()))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String reissueAccessToken(Long userId) {
        return tokenService.createAccessToken(userId);
    }

    private String reissueRefreshToken(Long userId) {
        return tokenService.createRefreshToken(userId);
    }

    /**
     * 클라이언트에게 access token과 refresh token을 RTR 방식으로 재발급합니다.
     */
    private void reissueTokens(HttpServletResponse response, Long userId) {
        String reissuedAccessToken = reissueAccessToken(userId);
        String reissuedRefreshToken = reissueRefreshToken(userId);
        tokenService.sendAccessTokenAndRefreshToken(response, reissuedAccessToken, reissuedRefreshToken);
        tokenService.updateRefreshToken(userId, reissuedRefreshToken);
    }

    private void rejectRequest(HttpServletResponse response) throws IOException {
        log.info("token checking failed");
        response.sendError(SC_UNAUTHORIZED, "토큰을 확인해주세요.");
    }
}
