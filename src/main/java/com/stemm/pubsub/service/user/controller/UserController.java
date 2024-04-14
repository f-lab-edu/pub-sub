package com.stemm.pubsub.service.user.controller;

import com.stemm.pubsub.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/jwt-test")
    public String testJwt() {
        return "JWT test 요청 성공";
    }
}
