package com.example.boardlab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginSuccess() throws Exception {
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"test@gmail.com","password":"test1234"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("login_success")))
                .andExpect(jsonPath("$.data.user_id", is(1)));
    }

    @Test
    void wrongPasswordReturns401() throws Exception {
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"test@gmail.com","password":"wrong123"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("email_password_check")));
    }

    @Test
    void duplicateEmailReturnsValidationError() throws Exception {
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email":"test@gmail.com",
                                  "password":"Test1234!",
                                  "password_check":"Test1234!",
                                  "nickname":"new-user"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].reason", is("email_duplicate")));
    }

    @Test
    void postListRequiresLoginHeader() throws Exception {
        mockMvc.perform(get("/posts?page=1"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message", is("unauthorized")));
    }

    @Test
    void otherUserCannotUpdatePost() throws Exception {
        mockMvc.perform(patch("/posts/1")
                        .header("X-USER-ID", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"수정 시도"}
                                """))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is("forbidden_author")));
    }

    @Test
    void createPostReturns201AndSnakeCaseFields() throws Exception {
        mockMvc.perform(post("/posts")
                        .header("X-USER-ID", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"새 글","content":"새 글 내용","image_url":null}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("posts_add_success")))
                .andExpect(jsonPath("$.data.post_id").isNumber());
    }

    @Test
    void commentCannotBeCreatedOnMissingPost() throws Exception {
        mockMvc.perform(post("/posts/999/comments")
                        .header("X-USER-ID", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"content":"댓글"}
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("posts_not_found")));
    }
}
