package com.stemm.pubsub.service.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
    @Max(value = 100, message = "닉네임은 100글자 이하로 설정해주세요.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname,

    @NotBlank(message = "이름을 입력해주세요.")
    String name,

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    String email,

    @NotBlank(message = "비밀번호를을 입력해주세요.")
    String password,

    String bio
) {
}
