package com.stemm.pubsub.service.auth;

import com.stemm.pubsub.service.auth.dto.ApiResponse;
import com.stemm.pubsub.service.auth.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.stemm.pubsub.service.auth.dto.ApiResponse.failure;
import static com.stemm.pubsub.service.auth.dto.ApiResponse.success;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        try {
            authService.signUp(signUpRequest);
            return ResponseEntity
                .status(CREATED)
                .body(success("회원가입에 성공하였습니다."));
        } catch (DuplicateValueException e) {
            return ResponseEntity
                .badRequest()
                .body(failure(e.getMessage()));
        }
    }
}
