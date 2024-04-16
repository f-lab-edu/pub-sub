package com.stemm.pubsub.service.auth;

import com.stemm.pubsub.service.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Validated @RequestBody SignUpRequest signUpRequest) throws Exception {
        authService.signUp(signUpRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "token-test 요청 성공";
    }
}
