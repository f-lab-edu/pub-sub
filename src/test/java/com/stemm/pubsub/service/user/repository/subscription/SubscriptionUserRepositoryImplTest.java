package com.stemm.pubsub.service.user.repository.subscription;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.user.entity.Membership;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.entity.subscription.Subscription;
import com.stemm.pubsub.service.user.repository.subscription.dto.SubscriptionUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;

class SubscriptionUserRepositoryImplTest extends RepositoryTestSupport {

    @Test
    @DisplayName("구독 중인 멤버십을 생성한 유저의 Id를 조회합니다.")
    void findSubscribingUserIds() {
        // given
        Membership membership1 = new Membership("mem1", 10_000);
        Membership membership2 = new Membership("mem2", 20_000);
        membershipRepository.saveAll(List.of(membership1, membership2));

        User user = createUser("user", "name1", "user1@me.com", null);
        User membershipUser1 = createUser("memUser1", "memName1", "memName1@me.com", membership1);
        User membershipUser2 = createUser("memUser2", "memName2", "memName2@me.com", membership2);
        userRepository.saveAll(List.of(user, membershipUser1, membershipUser2));

        Subscription subscription1 = new Subscription(user, membership1, ACTIVE);
        Subscription subscription2 = new Subscription(user, membership2, ACTIVE);
        subscriptionRepository.saveAll(List.of(subscription1, subscription2));

        entityManager.clear();

        // when
        SubscriptionUserDto subscribingUsersId = subscriptionRepository.findSubscribingUserIds(user.getId());

        // then
        Assertions.assertThat(subscribingUsersId.userIds())
            .contains(membershipUser1.getId(), membershipUser2.getId());
    }

    private User createUser(String nickname, String name, String email, Membership membership) {
        return User.builder()
            .nickname(nickname)
            .name(name)
            .email(email)
            .membership(membership)
            .password("password")
            .build();
    }
}