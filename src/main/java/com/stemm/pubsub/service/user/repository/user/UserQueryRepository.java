package com.stemm.pubsub.service.user.repository.user;

public interface UserQueryRepository {
    UserRepositoryDto findByUserId(Long userId);
    UserRepositoryDto findByNickname(String nickname);
}
