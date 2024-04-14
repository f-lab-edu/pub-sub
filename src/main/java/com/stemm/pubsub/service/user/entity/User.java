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

    /**
     * 유저가 생성한 멤버십
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Column(nullable = false, unique = true, length = 100)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String profileImageUrl;

    @Lob
    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String providerId;

    private String refreshToken;

    @Builder
    private User(
        Membership membership,
        String nickname,
        String name,
        String email,
        String password,
        String profileImageUrl,
        String bio,
        Role role,
        ProviderType providerType,
        String providerId,
        String refreshToken
    ) {
        this.membership = membership;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.role = role;
        this.providerType = providerType;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
