package com.stemm.pubsub.service.post.repository.post;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.post.dto.response.PostResponseDto;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.entity.post.PostLike;
import com.stemm.pubsub.service.user.entity.Membership;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.entity.subscription.Subscription;
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
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.CANCELED;
import static org.assertj.core.api.Assertions.assertThat;

class PostVisibilityBasedQueryRepositoryImplTest extends RepositoryTestSupport {

    @BeforeEach
    void setUp() {
        Membership membership1 = new Membership("membership1", 10_000);
        Membership membership2 = new Membership("membership2", 50_000);
        Membership membership3 = new Membership("membership3", 8_000);
        membershipRepository.saveAll(List.of(membership1, membership2, membership3));

        User user1 = createUser("user1", "name1", "user1@email.com", membership1);
        User user2 = createUser("user2", "name2", "user2@email.com", membership2);
        User user3 = createUser("user3", "name3", "user3@email.com", membership3);
        User user4 = createUser("user4", "name4", "user4@email.com", null);
        userRepository.saveAll(List.of(user1, user2, user3, user4));

        Subscription subscription1 = new Subscription(user4, membership1, ACTIVE);
        Subscription subscription2 = new Subscription(user4, membership2, ACTIVE);
        Subscription subscription3 = new Subscription(user4, membership3, CANCELED);
        subscriptionRepository.saveAll(List.of(subscription1, subscription2, subscription3));

        Post post1 = new Post(user1, "content1", null, PUBLIC);
        Post post2 = new Post(user1, "content2", null, PRIVATE);
        Post post3 = new Post(user2, "content3", null, PUBLIC);
        Post post4 = new Post(user3, "content4", null, PRIVATE);
        Post post5 = new Post(user3, "content5", null, PUBLIC);
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        PostLike postLike1 = new PostLike(post1, user1, LIKE);
        PostLike postLike2 = new PostLike(post1, user2, LIKE);
        PostLike postLike3 = new PostLike(post1, user3, DISLIKE);
        PostLike postLike4 = new PostLike(post2, user1, LIKE);
        PostLike postLike5 = new PostLike(post2, user3, DISLIKE);
        PostLike postLike6 = new PostLike(post3, user1, LIKE);
        PostLike postLike7 = new PostLike(post4, user1, DISLIKE);
        postLikeRepository.saveAll(List.of(postLike1, postLike2, postLike3, postLike4, postLike5, postLike6, postLike7));
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
            .extracting("content")
            .containsExactly("content5", "content3", "content1");
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
            .extracting("content")
            .containsExactly("content4", "content2");
    }

    private User createUser(String nickname, String name, String email, Membership membership) {
        return User.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .membership(membership)
            .build();
    }
}