package ru.yandex.practicum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.dto.CommentDto;
import ru.yandex.practicum.service.CommentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CommentController commentController;

    @MockitoBean
    private CommentService commentService;

    @Test
    public void shouldCreateCommentAndRedirectToPostPage() throws Exception {
        doNothing().when(commentService).save(any());
        mvc.perform(post("/post/1/comment/create").flashAttr("commentDto", new CommentDto()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/1"));
        verify(commentService, times(1)).save(any());
    }

    @Test
    public void shouldUpdateComment() throws Exception {
        doNothing().when(commentService).updateCommentText(eq(2L), eq("new-text"));
        mvc.perform(put("/post/1/comment/update").param("id", "2").param("text", "new-text"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).updateCommentText(eq(2L), eq("new-text"));
    }
}