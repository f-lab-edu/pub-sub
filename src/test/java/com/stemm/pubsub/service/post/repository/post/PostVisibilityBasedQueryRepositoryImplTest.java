package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.post.dto.response.PostResponseDto;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.entity.post.PostLike;
import com.stemm.pubsub.service.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.stemm.pubsub.service.post.entity.post.LikeStatus.DISLIKE;
import static com.stemm.pubsub.service.post.entity.post.LikeStatus.LIKE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PRIVATE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class PostVisibilityBasedQueryRepositoryImplTest extends RepositoryTestSupport {

    @BeforeEach
    void setUp() {
        User user1 = createUser("user1", "name1", "user1@email.com");
        User user2 = createUser("user2", "name2", "user2@email.com");
        User user3 = createUser("user3", "name3", "user3@email.com");
        User user4 = createUser("user4", "name4", "user4@email.com");
        userRepository.saveAll(List.of(user1, user2, user3, user4));

        Post post1 = new Post(user1, "content1", null, PUBLIC);
        Post post2 = new Post(user1, "content2", null, PRIVATE);
        Post post3 = new Post(user2, "content3", null, PUBLIC);
        Post post4 = new Post(user3, "content4", null, PRIVATE);
        Post post5 = new Post(user3, "content5", null, PUBLIC);
        post1.addLike(new PostLike(post1, user1, LIKE));
        post1.addLike(new PostLike(post1, user2, LIKE));
        post1.addLike(new PostLike(post1, user3, DISLIKE));
        post2.addLike(new PostLike(post2, user1, LIKE));
        post2.addLike(new PostLike(post2, user3, DISLIKE));
        post3.addLike(new PostLike(post3, user1, LIKE));
        post4.addLike(new PostLike(post4, user1, DISLIKE));
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));
    }

    @Test
    @DisplayName("모든 `PUBLIC` 게시물을 최신 순으로 조회합니다.")
    void findAllPublicPosts() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        entityManager.clear();

        // when
        Page<PostResponseDto> posts = postRepository.findAllPublicPosts(pageable);

        // then
        assertThat(posts)
            .hasSize(3)
            .extracting("content", "likeCount", "dislikeCount")
            .containsExactly(
                tuple("content5", 0, 0),
                tuple("content3", 1, 0),
                tuple("content1", 2, 1)
            );
    }

    @Test
    @DisplayName("모든 `PRIVATE` 게시물을 최신 순으로 조회합니다.")
    void findAllPrivatePosts() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        entityManager.clear();

        // when
        Page<PostResponseDto> posts = postRepository.findAllPrivatePosts(pageable);

        // then
        assertThat(posts)
            .hasSize(2)
            .extracting("content", "likeCount", "dislikeCount")
            .containsExactly(
                tuple("content4", 0, 1),
                tuple("content2", 1, 1)
            );
    }

    private User createUser(String nickname, String name, String email) {
        return User.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .build();
    }
}