package com.stemm.pubsub.service.post.controller;

import com.stemm.pubsub.common.ControllerTestSupport;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.entity.post.Visibility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.stemm.pubsub.service.post.entity.post.Visibility.PRIVATE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("전체 `PUBLIC` 게시물을 조회합니다.")
    void getAllPublicPosts() throws Exception {
        // given
        List<PostResponse> posts = List.of(
            createPostResponse(1L, "nickname1", "content1", PUBLIC),
            createPostResponse(2L, "nickname2", "content2", PUBLIC),
            createPostResponse(3L, "nickname3", "content3", PUBLIC)
        );
        Page<PostResponse> page = new PageImpl<>(posts);

        given(postService.getAllPublicPosts(any(Pageable.class)))
            .willReturn(page);

        // when & then
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @DisplayName("사용자가 구독 중인 크리에이터의 멤버십(private) 게시물을 조회합니다.")
    void getSubscribingMembershipPosts() throws Exception {
        // given
        List<PostResponse> posts = List.of(
            createPostResponse(1L, "nickname1", "content1", PRIVATE),
            createPostResponse(2L, "nickname2", "content2", PRIVATE),
            createPostResponse(3L, "nickname3", "content3", PRIVATE)
        );
        Page<PostResponse> page = new PageImpl<>(posts);

        given(postService.getSubscribingMembershipPosts(anyLong(), any(Pageable.class)))
            .willReturn(page);

        // when & then
        mockMvc.perform(get("/subscribed"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content").isArray());
    }

    @Test
    @DisplayName("게시물을 생성합니다.")
    void createPost() throws Exception {
        // given
        PostRequest postRequest = new PostRequest("content1", null, PUBLIC);

        // when & then
        mockMvc
            .perform(
                post("/posts")
                    .content(objectMapper.writeValueAsString(postRequest))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("게시물이 생성되었습니다."));
    }

    @Test
    @DisplayName("게시물의 내용은 비어 있을 수 없습니다.")
    void cantCreatePostWithEmptyContent() throws Exception {
        // given
        PostRequest postRequest = new PostRequest("", null, PUBLIC);

        // when & then
        mockMvc.perform(
                post("/posts")
                    .content(objectMapper.writeValueAsString(postRequest))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error[0]").value("게시물의 내용은 비어 있을 수 없습니다."));
    }

    @Test
    @DisplayName("게시물의 공개 범위는 PUBLIC 또는 PRIVATE 이어야 합니다.")
    void cantCreatePostWithoutVisibility() throws Exception {
        // given
        PostRequest postRequest = new PostRequest("content", null, null);

        // when & then
        mockMvc.perform(
                post("/posts")
                    .content(objectMapper.writeValueAsString(postRequest))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error[0]").value("게시물의 공개 범위는 PUBLIC 또는 PRIVATE 이어야 합니다."));
    }

    private PostResponse createPostResponse(Long id, String nickname, String content, Visibility visibility) {
        return new PostResponse(id, nickname, null, content, null, visibility, 0, 0, now(), now());
    }
}