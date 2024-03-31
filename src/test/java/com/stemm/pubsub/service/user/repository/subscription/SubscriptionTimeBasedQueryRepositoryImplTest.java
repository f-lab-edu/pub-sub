package com.stemm.pubsub.service.user.repository.subscription;

import com.stemm.pubsub.common.RepositoryTestSupport;
import com.stemm.pubsub.service.user.entity.Membership;
import com.stemm.pubsub.service.user.entity.User;
import com.stemm.pubsub.service.user.entity.subscription.Subscription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.stemm.pubsub.service.user.entity.subscription.SubscriptionStatus.ACTIVE;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionTimeBasedQueryRepositoryImplTest extends RepositoryTestSupport {

    @Test
    @DisplayName("유저가 구독한 멤버십을 최신 순으로 조회합니다.")
    void findNewestSubscriptions() {
        // given
        User user = User.builder()
            .nickname("a")
            .name("user")
            .email("a@me.com")
            .build();
        userRepository.save(user);

        Membership membership1 = new Membership("membership1", 10_000);
        Membership membership2 = new Membership("membership2", 50_000);
        Membership membership3 = new Membership("membership3", 8_000);
        membershipRepository.saveAll(List.of(membership1, membership2, membership3));

        Subscription subscription1 = new Subscription(user, membership1, ACTIVE);
        Subscription subscription2 = new Subscription(user, membership2, ACTIVE);
        Subscription subscription3 = new Subscription(user, membership3, ACTIVE);
        subscriptionRepository.saveAll(List.of(subscription1, subscription2, subscription3));

        // when
        List<Subscription> newestSubscriptions = subscriptionRepository.findNewestSubscriptions(user.getId());

        // then
        assertThat(newestSubscriptions)
            .hasSize(3)
            .extracting(Subscription::getCreatedDate)
            .isSortedAccordingTo(reverseOrder());
    }

    @Test
    @DisplayName("유저가 구독한 멤버십을 오래된 순으로 조회합니다.")
    void findOldestSubscriptions() {
        // given
        User user = User.builder()
            .nickname("a")
            .name("user")
            .email("a@me.com")
            .build();
        userRepository.save(user);

        Membership membership1 = new Membership("membership1", 10_000);
        Membership membership2 = new Membership("membership2", 50_000);
        Membership membership3 = new Membership("membership3", 8_000);
        membershipRepository.saveAll(List.of(membership1, membership2, membership3));

        Subscription subscription1 = new Subscription(user, membership1, ACTIVE);
        Subscription subscription2 = new Subscription(user, membership2, ACTIVE);
        Subscription subscription3 = new Subscription(user, membership3, ACTIVE);
        subscriptionRepository.saveAll(List.of(subscription1, subscription2, subscription3));

        // when
        List<Subscription> oldestSubscriptions = subscriptionRepository.findOldestSubscriptions(user.getId());

        // then
        assertThat(oldestSubscriptions)
            .hasSize(3)
            .extracting(Subscription::getCreatedDate)
            .isSortedAccordingTo(naturalOrder());
    }
}