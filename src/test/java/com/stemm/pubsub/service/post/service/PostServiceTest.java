package com.stemm.pubsub.service.post.service;

import com.stemm.pubsub.common.IntegrationTestSupport;
import com.stemm.pubsub.common.exception.UnauthorizedUserException;
import com.stemm.pubsub.common.exception.UserNotFoundException;
import com.stemm.pubsub.service.post.dto.PostRequest;
import com.stemm.pubsub.service.post.dto.PostResponse;
import com.stemm.pubsub.service.post.entity.post.Post;
import com.stemm.pubsub.service.post.entity.post.Visibility;
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

import static com.stemm.pubsub.service.post.entity.post.Visibility.PRIVATE;
import static com.stemm.pubsub.service.post.entity.post.Visibility.PUBLIC;
import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;
import static org.assertj.core.api.Assertions.*;

class PostServiceTest extends IntegrationTestSupport {

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {
        Membership user1sMembership = new Membership("user1's membership", 10_000);
        Membership user2sMembership = new Membership("user2's membership", 10_000);
        membershipRepository.saveAll(List.of(user1sMembership, user2sMembership));

        user1 = createUser(user1sMembership, "user1", "username1", "user1@me.com");
        user2 = createUser(user2sMembership, "user2", "username2", "user2@me.com");
        user3 = createUser(null, "user3", "username3", "user3@me.com");
        userRepository.saveAll(List.of(user1, user2, user3));

        Subscription user3sSubscription = new Subscription(user3, user1sMembership, ACTIVE);
        subscriptionRepository.save(user3sSubscription);
    }

    @Test
    @DisplayName("신규 게시물을 생성합니다.")
    void createPost() {
        // given
        PostRequest postRequest = createPostRequest("content", PRIVATE);

        // when
        Long postId = postService.createPost(postRequest, user1.getId());

        // then
        Post post = postRepository.findById(postId).get();
        assertThat(postId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("존재하지 않는 사용자는 게시물을 생성할 수 없습니다.")
    void cantCreatePost() {
        // given
        PostRequest postRequest = createPostRequest("content", PRIVATE);

        // when & then
        assertThatThrownBy(() -> postService.createPost(postRequest, 100L))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("특정 게시물을 조회합니다.")
    void getPost() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PRIVATE);
        postRepository.saveAll(List.of(post1, post2));

        // when
        PostResponse postResponse = postService.getPost(post1.getId());

        // then
        assertThat(postResponse)
            .extracting("id", "content", "visibility", "likeCount", "dislikeCount")
            .contains(post1.getId(), postResponse.content(), postResponse.visibility(), 0, 0);
    }

    @Test
    @DisplayName("존재하지 않는 게시물은 조회할 수 없습니다.")
    void cantGetPost() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PRIVATE);
        postRepository.saveAll(List.of(post1, post2));

        // when & then
        assertThatThrownBy(() -> postService.getPost(100L))
            .isInstanceOf(PostNotFoundException.class)
            .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("게시물을 수정합니다.")
    void updatePost() {
        // given
        Post post = createPost(user1, "post1", PRIVATE);
        Post savedPost = postRepository.save(post);
        PostRequest postRequest = createPostRequest("update post", PUBLIC);

        // when
        PostResponse postResponse = postService.updatePost(user1.getId(), savedPost.getId(), postRequest);

        // then
        assertThat(postResponse)
            .extracting("content", "visibility")
            .contains("update post", PUBLIC);
    }

    @Test
    @DisplayName("본인이 아니면 게시물을 수정할 수 없습니다.")
    void cantUpdateOtherUsersPost() {
        // given
        Post post = createPost(user1, "post1", PRIVATE);
        Post savedPost = postRepository.save(post);
        PostRequest postRequest = createPostRequest("update post", PUBLIC);

        // when & then
        assertThatThrownBy(() -> postService.updatePost(user2.getId(), savedPost.getId(), postRequest))
            .isInstanceOf(UnauthorizedUserException.class)
            .hasMessage("권한이 없는 사용자입니다.");
    }

    @Test
    @DisplayName("다른 사용자의 게시물은 수정할 수 없습니다")
    void cantUpdateNonExistingPost() {
        // given
        PostRequest postRequest = createPostRequest("update post", PUBLIC);

        // when & then
        assertThatThrownBy(() -> postService.updatePost(user1.getId(), -1L, postRequest))
            .isInstanceOf(PostNotFoundException.class)
            .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("게시물을 삭제합니다.")
    void deletePost() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PRIVATE);
        postRepository.saveAll(List.of(post1, post2));

        // when
        postService.deletePost(user1.getId(), post1.getId());

        // then
        List<Post> posts = postRepository.findAll();

        assertThat(posts)
            .hasSize(1)
            .extracting("content", "visibility")
            .containsExactly(tuple("post2", PRIVATE));
    }

    @Test
    @DisplayName("존재하지 않는 게시물은 삭제할 수 없습니다.")
    void cantDeleteNonExistingPost() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PRIVATE);
        postRepository.saveAll(List.of(post1, post2));

        // when & then
        assertThatThrownBy(() -> postService.deletePost(user1.getId(), -1L))
            .isInstanceOf(PostNotFoundException.class)
            .hasMessage("존재하지 않는 게시물입니다.");
    }

    @Test
    @DisplayName("다른 사용자의 게시물은 삭제할 수 없습니다.")
    void cantDeleteOtherUsersPost() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PRIVATE);
        postRepository.saveAll(List.of(post1, post2));

        // when & then
        assertThatThrownBy(() -> postService.deletePost(user2.getId(), post1.getId()))
            .isInstanceOf(UnauthorizedUserException.class)
            .hasMessage("권한이 없는 사용자입니다.");
    }

    @Test
    @DisplayName("사용자가 구독 중인 크리에이터의 멤버십(private) 게시물을 조회합니다.")
    void getSubscribingMembershipPosts() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PUBLIC);
        Post post3 = createPost(user2, "post3", PRIVATE);
        Post post4 = createPost(user2, "post4", PUBLIC);
        postRepository.saveAll(List.of(post1, post2, post3, post4));

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<PostResponse> posts = postService.getSubscribingMembershipPosts(user3.getId(), pageable);

        // then
        assertThat(posts.getContent())
            .hasSize(1)
            .extracting("content", "visibility")
            .containsExactly(tuple("post1", PRIVATE));
    }

    @Test
    @DisplayName("전체 `PUBLIC` 게시물을 조회합니다.")
    void getAllPublicPosts() {
        // given
        Post post1 = createPost(user1, "post1", PRIVATE);
        Post post2 = createPost(user1, "post2", PUBLIC);
        Post post3 = createPost(user2, "post3", PUBLIC);
        Post post4 = createPost(user2, "post4", PUBLIC);
        postRepository.saveAll(List.of(post1, post2, post3, post4));

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<PostResponse> posts = postService.getAllPublicPosts(pageable);

        // then
        assertThat(posts.getContent())
            .hasSize(3)
            .extracting("content", "visibility")
            .containsExactlyInAnyOrder(
                tuple("post2", PUBLIC),
                tuple("post3", PUBLIC),
                tuple("post4", PUBLIC)
            );
    }

    private User createUser(Membership membership, String nickname, String name, String email) {
        return User.builder()
            .membership(membership)
            .nickname(nickname)
            .name(name)
            .email(email)
            .password("password")
            .build();
    }

    private Post createPost(User user, String content, Visibility visibility) {
        return new Post(user, content, null, visibility);
    }

    private PostRequest createPostRequest(String content, Visibility visibility) {
        return new PostRequest(content, null, visibility);
    }
}