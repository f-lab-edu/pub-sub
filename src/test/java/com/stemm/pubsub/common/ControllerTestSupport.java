package com.stemm.pubsub.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stemm.pubsub.common.security.WithMockCustomUser;
import com.stemm.pubsub.service.post.controller.PostController;
import com.stemm.pubsub.service.post.service.PostService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PostController.class)
@WithMockCustomUser
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PostService postService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(springSecurity())
            .defaultRequest(get("/**").with(csrf()))
            .defaultRequest(post("/**").with(csrf()))
            .defaultRequest(patch("/**").with(csrf()))
            .defaultRequest(delete("/**").with(csrf()))
            .build();
    }
}
