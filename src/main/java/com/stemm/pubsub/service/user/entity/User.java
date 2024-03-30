package com.stemm.pubsub.service.user.entity;

import com.stemm.pubsub.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
    name = "users",
    indexes = @Index(name = "idx_users_created_date", columnList = "created_date")
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String profileImageUrl;

    @Lob
    private String bio;

    @Builder
    private User(
        Membership membership,
        String username,
        String name,
        String email,
        String profileImageUrl,
        String bio
    ) {
        this.membership = membership;
        this.username = username;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
    }
}
