package com.stemm.pubsub.post.entity;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.user.entity.User;
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
@Table(indexes = @Index(name = "idx_post_created_date", columnList = "created_date"))
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Visibility visibility;

    public Post(User user, String content, String imageUrl, Visibility visibility) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.visibility = visibility;
    }
}
