package com.stemm.pubsub.common;

import com.stemm.pubsub.service.post.repository.post.PostRepository;
import com.stemm.pubsub.service.post.service.PostService;
import com.stemm.pubsub.service.user.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected PostService postService;
}