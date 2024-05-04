package com.stemm.pubsub.common.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stemm.pubsub.common.security.WithMockCustomUser;
import com.stemm.pubsub.service.post.controller.PostController;
import com.stemm.pubsub.service.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(PostController.class)
@WithMockCustomUser
public abstract class RestDocsSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PostService postService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(
                documentationConfiguration(provider)
                    .operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build();
    }
}
