package com.stemm.pubsub.service.user.repository;

import com.stemm.pubsub.service.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByNickname(String nickname);
}
