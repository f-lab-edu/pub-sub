package com.stemm.pubsub.service.auth.dto;

public record SignUpRequest(
    String nickname,
    String name,
    String email,
    String password,
    String bio
) {
}
