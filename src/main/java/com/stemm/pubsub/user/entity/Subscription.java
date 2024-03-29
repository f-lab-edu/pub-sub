package com.stemm.pubsub.user.entity;

import com.stemm.pubsub.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
@Table(indexes = @Index(name = "idx_subscription_created_date", columnList = "created_date"))
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    public Subscription(User user, Membership membership, SubscriptionStatus status) {
        this.user = user;
        this.membership = membership;
        this.status = status;
    }
}
