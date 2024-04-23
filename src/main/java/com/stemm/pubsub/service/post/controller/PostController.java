package com.stemm.pubsub.service.post.controller;

import com.stemm.pubsub.service.auth.dto.ApiResponse;
import com.stemm.pubsub.service.auth.userdetails.CustomUserDetails;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.service.PostNotFoundException;
import com.stemm.pubsub.service.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.stemm.pubsub.service.auth.dto.ApiResponse.error;
import static com.stemm.pubsub.service.auth.dto.ApiResponse.success;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAllPublicPosts(Pageable pageable) {
        Page<PostResponse> posts = postService.getAllPublicPosts(pageable);
        return ResponseEntity.ok(success(posts));
    }

    @GetMapping("/subscribed")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getSubscribingMembershipPosts(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Pageable pageable
    ) {
        Long userId = userDetails.getUserId();
        Page<PostResponse> posts = postService.getSubscribingMembershipPosts(userId, pageable);
        return ResponseEntity.ok(success(posts));
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> createPost(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Validated @RequestBody PostRequest postRequest
    ) {
        Long userId = userDetails.getUserId();
        Long postId = postService.createPost(postRequest, userId);

        return ResponseEntity
            .created(URI.create("/posts/" + postId))
            .body(success("게시물이 생성되었습니다."));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(success(post));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long postId,
        @Validated @RequestBody PostRequest postRequest
    ) {
        Long userId = userDetails.getUserId();
        PostResponse post = postService.updatePost(userId, postId, postRequest);
        return ResponseEntity.ok(success("게시물이 수정되었습니다.", post));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long postId
    ) {
        Long userId = userDetails.getUserId();
        postService.deletePost(userId, postId);
        return ResponseEntity.ok(success("게시물이 삭제되었습니다."));
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse<Void>> handlePostNotFoundException(PostNotFoundException e) {
        log.error("존재하지 않는 게시물입니다. post id = {}", e.getPostId());

        return ResponseEntity
            .status(NOT_FOUND)
            .body(error(e.getMessage()));
    }
}
