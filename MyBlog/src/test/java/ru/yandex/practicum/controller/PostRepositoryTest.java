package ru.yandex.practicum.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.TestConfiguration;
import ru.yandex.practicum.configuration.DataBaseSchemaInitializer;
import ru.yandex.practicum.dto.domain.Post;
import ru.yandex.practicum.extractor.CommentResultSetExtractor;
import ru.yandex.practicum.extractor.PostResultSetExtractor;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.repository.PostRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestConfiguration.class,
        PostRepository.class,
        CommentRepository.class,
        CommentResultSetExtractor.class,
        PostResultSetExtractor.class,
        DataBaseSchemaInitializer.class
})
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private PostResultSetExtractor postResultSetExtractor;

    @Test
    public void shouldExtractPostData() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(eq(Post.PostColumn.ID.getColumnName()))).thenReturn(5L);
        when(resultSet.getString(eq(Post.PostColumn.TITLE.getColumnName()))).thenReturn("title_test");
        when(resultSet.getString(eq(Post.PostColumn.CONTENT.getColumnName()))).thenReturn("content_test");
        when(resultSet.getString(eq(Post.PostColumn.IMAGE.getColumnName()))).thenReturn("test.jpg");
        when(resultSet.getLong(eq(Post.PostColumn.LIKES_COUNT.getColumnName()))).thenReturn(0L);
        when(resultSet.getArray(eq(Post.PostColumn.TAGS.getColumnName()))).thenReturn(null);

        List<Post> methodResult = postResultSetExtractor.extractData(resultSet);
        assertNotNull(methodResult);
        assertEquals(1, methodResult.size());

        Post post = methodResult.get(0);
        assertEquals(5L, post.getId());
        assertEquals("test.jpg", post.getImage());
    }

    @Test
    public void shouldSavePost() {
        List<Post> postInDbBeforeTest = namedParameterJdbcTemplate.query("SELECT * FROM POST", postResultSetExtractor);
        assertEquals(0, postInDbBeforeTest.size());

        Post post = new Post()
                .setTitle("title")
                .setContent("content")
                .setImage("image")
                .setLikesCount(0L);

        postRepository.save(post);

        List<Post> postInDbAfterTest = namedParameterJdbcTemplate.query("SELECT * FROM POST", postResultSetExtractor);
        assertEquals(1, postInDbAfterTest.size());
        assertEquals("title", postInDbAfterTest.get(0).getTitle());
    }

}