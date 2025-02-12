package ru.yandex.practicum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.service.PostService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostController postController;

    @MockitoBean
    private PostService postService;

    @Test
    public void shouldRedirectToPostsPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/posts"));
    }

    @Test
    public void shouldFillModelAttributeWithDefaultValueForPostsPage() throws Exception {
        when(postService.findAll(any(), any(), any())).thenReturn(List.of());
        mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("new_post"))
                .andExpect(model().attribute("currentPageNum", 0))
                .andExpect(model().attribute("currentPageSize", 10));

    }

    @Test
    public void shouldSavePostAndRedirectToIndexPage() throws Exception {
        doNothing().when(postService).save(any(PostDto.class), any());
        mvc.perform(multipart("/post/create")
                        .file(new MockMultipartFile("image", new byte[]{}))
                        .param("title", "test-title")
                        .param("content", "test-content")
                        .param("tags", "test-tags;"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void shouldReturnDetailedPostInfo() throws Exception {
        var post = new PostDto().setContent("").setImage("");
        when(postService.findById(eq(1L))).thenReturn(post);
        mvc.perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post", "new_comment"))
                .andExpect(model().attribute("post", post));
    }

    @Test
    public void shouldDeletePost() throws Exception {
        doNothing().when(postService).deleteById(eq(1L));
        mvc.perform(post("/post/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}