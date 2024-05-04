package com.stemm.pubsub.service.post.service;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {

    private final Long postId;

    public PostNotFoundException(Long postId) {
        super("존재하지 않는 게시물입니다.");
        this.postId = postId;
    }
}
