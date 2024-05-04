package com.stemm.pubsub.service.user.service;

import com.stemm.pubsub.service.user.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


}
