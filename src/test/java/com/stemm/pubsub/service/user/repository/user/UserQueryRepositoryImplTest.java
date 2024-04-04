package com.stemm.pubsub.service.user.repository.user;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserQueryRepositoryImplTest extends RepositoryTestSupport {

    @Test
    @DisplayName("Id로 유저를 조회합니다.")
    void findUserById() {
        // given
        User user1 = createUser("user1", "name1", "user1@email.com");
        User user2 = createUser("user2", "name2", "user2@email.com");
        User user3 = createUser("user3", "name3", "user3@email.com");
        userRepository.saveAll(List.of(user1, user2, user3));

        // when
        UserRepositoryDto user = userRepository.findUserById(1L);

        // then
        assertThat(user)
            .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("닉네임으로 유저를 조회합니다.")
    void findUserByNickname() {
        // given
        String nickname = "user1";
        User user1 = createUser(nickname, "name1", "user1@email.com");
        User user2 = createUser("user2", "name2", "user2@email.com");
        User user3 = createUser("user3", "name3", "user3@email.com");
        userRepository.saveAll(List.of(user1, user2, user3));

        // when
        UserRepositoryDto user = userRepository.findUserByNickname(nickname);

        // then
        assertThat(user)
            .hasFieldOrPropertyWithValue("nickname", nickname);
    }

    private User createUser(String nickname, String name, String email) {
        return User.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .build();
    }
}