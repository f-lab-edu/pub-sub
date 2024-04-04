package com.stemm.pubsub.service.post.repository.comment;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.post.entity.Comment;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

class CommentPostRepositoryImplTest extends RepositoryTestSupport {

    @Test
    @DisplayName("특정 게시물의 댓글을 좋아요 순으로 조회합니다.")
    void findCommentsForPost() {
        // given
        User user = createUser();
        userRepository.save(user);

        Post post = new Post(user, "content1", null, PUBLIC);
        postRepository.save(post);

        Comment comment1 = new Comment(post, user, "comment1");
        Comment comment2 = new Comment(post, user, "comment2");
        Comment comment3 = new Comment(post, user, "comment3");
        commentRepository.saveAll(List.of(comment1, comment2, comment3));

        // when
        Page<CommentDto> comments = commentRepository.findCommentsForPost(
            post.getId(),
            PageRequest.of(0, 10)
        );

        // then
        assertThat(comments)
            .hasSize(3)
            .extracting("content")
            .containsExactly(comment3.getContent(), comment2.getContent(), comment1.getContent());
    }

    private User createUser() {
        return User.builder()
            .nickname("nickname")
            .name("name")
            .email("user@me.com")
            .build();
    }
}