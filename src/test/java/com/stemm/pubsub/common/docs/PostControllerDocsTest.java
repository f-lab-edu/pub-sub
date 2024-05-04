package com.stemm.pubsub.common.docs;

import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerDocsTest extends RestDocsSupport {

    @Test
    @DisplayName("전체 `PUBLIC` 게시물을 조회합니다.")
    void getAllPublicPosts() throws Exception {
        List<PostResponse> posts = List.of(
            createPostResponse()
        );
        Page<PostResponse> page = new PageImpl<>(posts);

        given(postService.getAllPublicPosts(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content").isArray())
            .andDo(
                document(
                    "getAllPublicPosts",
                    queryParameters(
                        parameterWithName("page").optional().description("페이지 번호"),
                        parameterWithName("size").optional().description("페이지 크기")
                    ),
                    responseFields(
                        fieldWithPath("data.content.[]").type(ARRAY).description("게시물 목록"),
                        fieldWithPath("data.content.[].id").type(NUMBER).description("게시물 ID"),
                        fieldWithPath("data.content.[].nickname").type(STRING).description("닉네임"),
                        fieldWithPath("data.content.[].profileImageUrl").type(STRING).optional().description("프로필 이미지 Url"),
                        fieldWithPath("data.content.[].content").type(STRING).description("내용"),
                        fieldWithPath("data.content.[].imageUrl").type(STRING).optional().description("이미지 URL"),
                        fieldWithPath("data.content.[].visibility").type(STRING).description("공개 범위(PRIVATE, PUBLIC)"),
                        fieldWithPath("data.content.[].likeCount").type(NUMBER).description("좋아요 수"),
                        fieldWithPath("data.content.[].dislikeCount").type(NUMBER).description("싫어요 수"),
                        fieldWithPath("data.content.[].createdDate").type(STRING).description("생성 날짜"),
                        fieldWithPath("data.content.[].lastModifiedDate").type(STRING).description("마지막 수정 날짜"),
                        fieldWithPath("data.totalPages").type(NUMBER).description("전체 페이지 수"),
                        fieldWithPath("data.totalElements").type(NUMBER).description("전체 게시물 수"),
                        fieldWithPath("data.pageable").type(STRING).description("현재 페이지에 대한 Pageable 인스턴스"),
                        fieldWithPath("data.last").type(BOOLEAN).description("마지막 페이지 여부"),
                        fieldWithPath("data.first").type(BOOLEAN).description("첫번째 페이지 여부"),
                        fieldWithPath("data.size").type(NUMBER).description("페이지에 있는 데이터 개수"),
                        fieldWithPath("data.number").type(NUMBER).description("현재 페이지 번호"),
                        fieldWithPath("data.sort").type(OBJECT).description("Sort 객체"),
                        fieldWithPath("data.sort.empty").type(BOOLEAN).description("empty"),
                        fieldWithPath("data.sort.sorted").type(BOOLEAN).description("sorted"),
                        fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("unsorted"),
                        fieldWithPath("data.numberOfElements").type(NUMBER).description("현재 페이지에 실제로 있는 데이터 수"),
                        fieldWithPath("data.empty").type(BOOLEAN).description("현재 페이지가 비어있는지 여부")
                    )
                )
            );
    }

    @Test
    @DisplayName("게시물을 생성합니다.")
    void createPost() throws Exception {
        PostRequest postRequest = new PostRequest("content", null, PUBLIC);

        given(postService.createPost(any(PostRequest.class), anyLong()))
            .willReturn(1L);

        mockMvc
            .perform(
                post("/posts")
                    .content(objectMapper.writeValueAsString(postRequest))
                    .contentType(APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("게시물이 생성되었습니다."))
            .andDo(
                document(
                    "createPost",
                    responseHeaders(
                        headerWithName("Location").description("생성된 게시물 Uri")
                    ),
                    requestFields(
                        fieldWithPath("content").type(STRING).description("상품 타입"),
                        fieldWithPath("imageUrl").type(STRING).optional().description("이미지 Url"),
                        fieldWithPath("visibility").type(STRING).description("공개 범위")
                    ),
                    responseFields(
                        fieldWithPath("message").type(STRING).description("메시지")
                    )
                )
            );
    }

    private PostResponse createPostResponse() {
        return new PostResponse(1L, "nickname", null, "content of the post", null, PUBLIC, 0, 0, now(), now());
    }
}
