package com.stemm.pubsub.service.post.repository.postlike;

public record PostLikeDto(
    Long postId,
    int likeCount,
    int dislikeCount
) {
    public PostLikeDto(Long postId, Long likeCount, Long dislikeCount) {
        this(postId, likeCount.intValue(), dislikeCount.intValue());
    }
}
