package com.stemm.pubsub.service.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

// TODO: SimpleUrlAuthenticationFailureHandler 굳이 상속해야하나? (success handler도 마찬가지)
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException {
        log.info("로그인에 실패했습니다. 메시지: {}", exception.getMessage());
        response.sendError(SC_UNAUTHORIZED, "로그인에 실패했습니다. 아이디나 비밀번호를 확인해주세요.");
    }
}
