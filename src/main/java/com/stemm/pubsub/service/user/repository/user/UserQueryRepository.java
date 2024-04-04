package com.stemm.pubsub.service.user.repository.user;

public interface UserQueryRepository {
    UserDto findByUserId(Long userId);
    UserDto findByNickname(String nickname);
}
