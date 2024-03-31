package com.stemm.pubsub.service.post.entity.post;

import com.stemm.pubsub.common.BaseEntity;
import com.stemm.pubsub.service.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(indexes = @Index(name = "idx_post_created_date", columnList = "created_date"))
@NoArgsConstructor(access = PROTECTED)
@Getter
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
    @Column(nullable = false, length = 10)
    private Visibility visibility;

    public Post(User user, String content, String imageUrl, Visibility visibility) {
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.visibility = visibility;
    }
}
