package com.stemm.pubsub.service.auth;

import com.stemm.pubsub.service.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // TODO: string 리턴 x
    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        authService.signUp(signUpRequest);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "token-test 요청 성공";
    }
}
