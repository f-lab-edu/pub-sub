package com.stemm.pubsub.service.post.service;

import com.stemm.pubsub.common.exception.UserNotFoundException;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.repository.post.PostDto;
import com.stemm.pubsub.service.post.repository.post.PostRepository;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // TODO: update, delete
    // TODO: 메인화면에서 쓸 게시물 (public, private) 조회

    @Transactional
    public Long createPost(PostRequest postRequest, Long userId) {
        User user = getUser(userId);
        Post post = toEntity(postRequest, user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        PostDto postDto = postRepository.findPostById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));

        return toPostResponse(postDto);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }

    // TODO: 서비스에서 변환 말고 따로
    private Post toEntity(PostRequest postRequest, User user) {
        return new Post(
            user,
            postRequest.content(),
            postRequest.imageUrl(),
            postRequest.visibility()
        );
    }

    // TODO: 서비스에서 변환 말고 따로
    private PostResponse toPostResponse(PostDto postDto) {
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
}
