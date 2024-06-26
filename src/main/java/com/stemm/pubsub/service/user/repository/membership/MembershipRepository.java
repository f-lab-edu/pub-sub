package com.stemm.pubsub.service.user.repository.membership;

import com.stemm.pubsub.service.user.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
