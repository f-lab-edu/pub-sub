package com.stemm.pubsub.service.user.repository.user;

public interface UserQueryRepository {
    UserRepositoryDto findUserById(Long userId);
    UserRepositoryDto findUserByNickname(String nickname);
}
