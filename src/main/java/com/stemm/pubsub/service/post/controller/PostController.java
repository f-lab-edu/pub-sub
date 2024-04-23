package com.stemm.pubsub.service.post.controller;

import com.stemm.pubsub.service.auth.dto.ApiResponse;
import com.stemm.pubsub.service.auth.userdetails.CustomUserDetails;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.service.PostNotFoundException;
import com.stemm.pubsub.service.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // TODO: AuthenticationPrincipal 중복 줄이기?
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
        try {
            PostResponse post = postService.getPost(postId);
            return ResponseEntity.ok(success(post));
        } catch (PostNotFoundException e) {
            log.error("존재하지 않는 게시물입니다. post id = {}", e.getPostId());
            return ResponseEntity
                .status(NOT_FOUND)
                .body(error(e.getMessage()));
        }
    }
}
