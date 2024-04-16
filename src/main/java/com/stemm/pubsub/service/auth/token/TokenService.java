package com.stemm.pubsub.service.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
@Service
@RequiredArgsConstructor
@Getter
public class TokenService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE = "refreshToken";
    public static final String URI_ALL = "/";
    public static final int COOKIE_EXPIRY = 7 * 24 * 60 * 60;  // 7일

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    private final UserRepository userRepository;

    public String createAccessToken(Long userId) {
        return JWT.create()
            .withSubject(String.valueOf(userId))
            .withExpiresAt(calculateTokenExpiry(accessTokenExpirationPeriod))
            .sign(HMAC512(secretKey));
    }

    public String createRefreshToken(Long userId) {
        return JWT.create()
            .withSubject(String.valueOf(userId))
            .withExpiresAt(calculateTokenExpiry(refreshTokenExpirationPeriod))
            .sign(HMAC512(secretKey));
    }

    private Date calculateTokenExpiry(long tokenExpirationPeriod) {
        return new Date(System.currentTimeMillis() + tokenExpirationPeriod);
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(SC_OK);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
    }

    public void sendAccessTokenAndRefreshToken(
        HttpServletResponse response,
        String accessToken,
        String refreshToken
    ) {
        sendAccessToken(response, accessToken);
        response.addCookie(makeRefreshTokenCookie(response, refreshToken));
    }

    private Cookie makeRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(URI_ALL);
        cookie.setMaxAge(COOKIE_EXPIRY);
        return cookie;
    }

    /**
     * 토큰 만료, 변조 등을 검사합니다.
     */
    public boolean isValidToken(String token) {
        try {
            JWT.require(HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
            .filter(accessHeader -> accessHeader.startsWith(BEARER_PREFIX))
            .map(accessHeader -> accessHeader.substring(BEARER_PREFIX.length()));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
            .stream()
            .flatMap(Arrays::stream)
            .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE))
            .findAny()
            .map(Cookie::getValue);
    }

    public Optional<Long> extractUserId(String token) {
        try {
            String subject = JWT.require(HMAC512(secretKey)).build().verify(token).getSubject();
            return Optional.ofNullable(subject).map(Long::valueOf);
        } catch (NumberFormatException e) {
            log.error("유효한 유저 id가 아닙니다: {}", e.getMessage());
            return Optional.empty();
        } catch (JWTVerificationException e) {
            log.error("access token이 유효하지 않습니다: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        userRepository.findById(userId)
            .ifPresentOrElse(
                user -> user.updateRefreshToken(refreshToken),
                () -> new IllegalArgumentException("일치하는 회원이 없습니다.")
            );
    }
}
