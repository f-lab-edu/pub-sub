package com.stemm.pubsub.service.user.repository.user;

import com.stemm.pubsub.service.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserInfoRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByProviderId(String providerId);
}
