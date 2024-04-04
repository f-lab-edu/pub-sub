package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.stemm.pubsub.service.post.entity.post.Visibility.PRIVATE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

class PostVisibilityRepositoryImplTest extends RepositoryTestSupport {

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        user1 = createUser("user1", "name1", "user1@email.com");
        user2 = createUser("user2", "name2", "user2@email.com");
        user3 = createUser("user3", "name3", "user3@email.com");
        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    @DisplayName("`PUBLIC` 게시물을 최신 순으로 조회합니다.")
    void findPublicPosts() {
        // given
        Post post1 = new Post(user1, "content1", null, PUBLIC);
        Post post2 = new Post(user1, "content2", null, PUBLIC);
        Post post3 = new Post(user2, "content3", null, PUBLIC);
        Post post4 = new Post(user3, "content4", null, PRIVATE);
        Post post5 = new Post(user3, "content5", null, PUBLIC);
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        entityManager.clear();

        // when
        Page<PostDto> posts = postRepository.findPublicPosts(PageRequest.of(0, 10));

        // then
        assertThat(posts)
            .hasSize(4)
            .extracting("id")
            .containsExactly(post5.getId(), post3.getId(), post2.getId(), post1.getId());
    }

    @Test
    @DisplayName("특정 유저들의 `PRIVATE` 게시물을 최신 순으로 조회합니다.")
    void findUsersPrivatePosts() {
        // given
        Post post1 = new Post(user1, "content1", null, PUBLIC);
        Post post2 = new Post(user1, "content2", null, PRIVATE);
        Post post3 = new Post(user2, "content3", null, PRIVATE);
        Post post4 = new Post(user3, "content4", null, PRIVATE);
        Post post5 = new Post(user3, "content5", null, PUBLIC);
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        List<Long> userIds = List.of(user1.getId(), user2.getId(), user3.getId());

        entityManager.clear();

        // when
        Page<PostDto> posts = postRepository.findUsersPrivatePosts(userIds, PageRequest.of(0, 10));

        // then
        assertThat(posts)
            .hasSize(3)
            .extracting("id")
            .containsExactly(post4.getId(), post3.getId(), post2.getId());
    }

    private User createUser(String nickname, String name, String email) {
        return User.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .build();
    }
}