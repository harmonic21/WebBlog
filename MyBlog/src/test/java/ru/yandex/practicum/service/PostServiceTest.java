package ru.yandex.practicum.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.dto.PostDto;
import ru.yandex.practicum.dto.domain.Post;
import ru.yandex.practicum.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PostServiceTest {

    private static final String POST_TITLE = "title";
    private static final String POST_CONTENT = "content";
    private static final String POST_TAGS = "one;two";
    private static final Long POST_LIKES = 0L;

    @MockitoBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Captor
    private ArgumentCaptor<Post> postCaptor;

    @Test
    public void shouldSavePost() {
        doNothing().when(postRepository).save(any());

        PostDto sourcePost = new PostDto()
                .setTitle(POST_TITLE)
                .setContent(POST_CONTENT)
                .setTags(POST_TAGS)
                .setLikesCount(POST_LIKES);

        postService.save(sourcePost, new MockMultipartFile("test", new byte[0]));

        verify(postRepository).save(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertEquals(POST_TITLE, capturedPost.getTitle());
        assertEquals(POST_CONTENT, capturedPost.getContent());
        assertEquals(2, capturedPost.getTags().length);
        assertEquals(POST_LIKES, capturedPost.getLikesCount());
    }
}