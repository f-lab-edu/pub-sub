package com.stemm.pubsub.service.user.entity.subscription;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubscription is a Querydsl query type for Subscription
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubscription extends EntityPathBase<Subscription> {

    private static final long serialVersionUID = -1940765095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscription subscription = new QSubscription("subscription");

    public final com.stemm.pubsub.common.QBaseEntity _super = new com.stemm.pubsub.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.stemm.pubsub.service.user.entity.QMembership membership;

    public final EnumPath<SubscriptionStatus> status = createEnum("status", SubscriptionStatus.class);

    public final com.stemm.pubsub.service.user.entity.QUser user;

    public QSubscription(String variable) {
        this(Subscription.class, forVariable(variable), INITS);
    }

    public QSubscription(Path<? extends Subscription> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscription(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscription(PathMetadata metadata, PathInits inits) {
        this(Subscription.class, metadata, inits);
    }

    public QSubscription(Class<? extends Subscription> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.membership = inits.isInitialized("membership") ? new com.stemm.pubsub.service.user.entity.QMembership(forProperty("membership")) : null;
        this.user = inits.isInitialized("user") ? new com.stemm.pubsub.service.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

