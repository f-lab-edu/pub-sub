package com.stemm.pubsub.common.exception;

import lombok.Getter;

@Getter
public class UnauthorizedUserException extends RuntimeException {

    private final Long userId;

    public UnauthorizedUserException(Long userId) {
        super("권한이 없는 사용자입니다.");
        this.userId = userId;
    }
}
