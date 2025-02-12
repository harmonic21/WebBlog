package ru.yandex.practicum.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.dto.domain.Post;
import ru.yandex.practicum.extractor.PostResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        List<Post> postInDbBeforeTest = namedParameterJdbcTemplate.query("SELECT * FROM POST WHERE title = 'test-post'", postResultSetExtractor);
        assertEquals(0, postInDbBeforeTest.size());

        Post post = new Post()
                .setTitle("test-post")
                .setContent("content")
                .setImage("image")
                .setLikesCount(0L);

        postRepository.save(post);

        List<Post> postInDbAfterTest = namedParameterJdbcTemplate.query("SELECT * FROM POST WHERE title = 'test-post'", postResultSetExtractor);
        assertEquals(1, postInDbAfterTest.size());
        assertEquals("test-post", postInDbAfterTest.get(0).getTitle());
    }
}