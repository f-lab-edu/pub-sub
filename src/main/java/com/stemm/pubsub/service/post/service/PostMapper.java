package com.stemm.pubsub.service.post.service;

import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.repository.post.PostDto;
import com.stemm.pubsub.service.post.repository.postlike.PostLikeDto;
import com.stemm.pubsub.service.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(User user, PostRequest postRequest) {
        return new Post(
            user,
            postRequest.content(),
            postRequest.imageUrl(),
            postRequest.visibility()
        );
    }

    public Post toEntity(User user, PostDto postDto) {
        return new Post(
            user,
            postDto.content(),
            postDto.imageUrl(),
            postDto.visibility()
        );
    }

    public PostResponse toPostResponse(PostDto postDto) {
        return new PostResponse(
            postDto.id(),
            postDto.nickname(),
            postDto.profileImageUrl(),
            postDto.content(),
            postDto.imageUrl(),
            postDto.visibility(),
            postDto.likeCount(),
            postDto.dislikeCount(),
            postDto.createdDate(),
            postDto.lastModifiedDate()
        );
    }

    public PostResponse toPostResponse(Post post, PostLikeDto postLikeDto) {
        return new PostResponse(
            post.getId(),
            post.getUser().getNickname(),
            post.getUser().getProfileImageUrl(),
            post.getContent(),
            post.getImageUrl(),
            post.getVisibility(),
            postLikeDto.likeCount(),
            postLikeDto.dislikeCount(),
            post.getCreatedDate(),
            post.getLastModifiedDate()
        );
    }
}
