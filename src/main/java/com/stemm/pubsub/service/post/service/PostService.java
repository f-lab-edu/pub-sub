package com.stemm.pubsub.service.post.service;

import com.stemm.pubsub.common.exception.UnauthorizedUserException;
import com.stemm.pubsub.common.exception.UserNotFoundException;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.repository.post.PostDto;
import com.stemm.pubsub.service.post.repository.post.PostRepository;
import com.stemm.pubsub.service.post.repository.postlike.PostLikeDto;
import com.stemm.pubsub.service.post.repository.postlike.PostLikeRepository;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    private final PostMapper postMapper;

    // TODO: 메인화면에서 쓸 게시물 (public, private) 조회

    @Transactional
    public Long createPost(PostRequest postRequest, Long userId) {
        User user = getUser(userId);
        Post post = postMapper.toEntity(user, postRequest);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        PostDto postDto = getPostDto(postId);
        return postMapper.toPostResponse(postDto);
    }

    // TODO: like count, dislike count 어떻게 처리하는게 좋을까...
    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostRequest postRequest) {
        Post post = getPostEntity(postId);
        validateUser(userId, post);
        post.update(postRequest.content(), postRequest.imageUrl(), postRequest.visibility());
        return postMapper.toPostResponse(post, getPostLikeDto(postId));
    }

    // TODO: Post 전체가 아닌 id만 가져오도록 변경?
    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = getPostEntity(postId);
        validateUser(userId, post);
        postRepository.delete(post);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void validateUser(Long userId, Post post) {
        if (!userId.equals(post.getUser().getId())) {
            throw new UnauthorizedUserException(userId);
        }
    }

    private PostDto getPostDto(Long postId) {
        return postRepository.findPostById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private PostLikeDto getPostLikeDto(Long postId) {
        return postLikeRepository.findLikeAndDislikeCountByPostId(postId);
    }
}
