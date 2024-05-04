package com.stemm.pubsub.common.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("존재하지 않는 사용자입니다.");
        this.userId = userId;
    }
}
