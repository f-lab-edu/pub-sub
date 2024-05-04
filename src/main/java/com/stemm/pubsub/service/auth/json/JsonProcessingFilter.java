package com.stemm.pubsub.service.auth.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URI = "/login";  // 해당 uri 요청에만 필터 작동
    private static final String POST = "POST";
    private static final String APPLICATION_JSON = "application/json";
    private static final String USERNAME = "nickname";
    private static final String PASSWORD = "password";

    private final ObjectMapper objectMapper;

    public JsonProcessingFilter(ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(LOGIN_URI, POST));
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException, IOException {
        if (!isJsonFormat(request)) {
            throw new AuthenticationServiceException(request.getContentType() + " 은(는) 지원되지 않는 content type 입니다.");
        }

        String body = StreamUtils.copyToString(request.getInputStream(), UTF_8);
        Map<String, String> map = objectMapper.readValue(body, Map.class);

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(
            map.get(USERNAME), map.get(PASSWORD)
        );

        return getAuthenticationManager().authenticate(authRequest);
    }

    private boolean isJsonFormat(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.equals(APPLICATION_JSON);
    }
}
