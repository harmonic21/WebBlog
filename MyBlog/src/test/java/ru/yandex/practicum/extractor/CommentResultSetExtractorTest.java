package ru.yandex.practicum.extractor;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.dto.domain.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommentResultSetExtractorTest {

    private static final CommentResultSetExtractor EXTRACTOR = new CommentResultSetExtractor();

    private static final Long COMMENT_ID = 1L;
    private static final Long POST_ID = 2L;
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    @Test
    public void shouldExtractCommentData() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(eq(Comment.CommentColumn.ID.getColumnName()))).thenReturn(COMMENT_ID);
        when(resultSet.getLong(eq(Comment.CommentColumn.POST_ID.getColumnName()))).thenReturn(POST_ID);
        when(resultSet.getString(eq(Comment.CommentColumn.AUTHOR.getColumnName()))).thenReturn(AUTHOR);
        when(resultSet.getString(eq(Comment.CommentColumn.COMMENT_CONTENT.getColumnName()))).thenReturn(CONTENT);

        List<Comment> methodResult = EXTRACTOR.extractData(resultSet);
        assertNotNull(methodResult);
        assertEquals(1, methodResult.size());

        Comment comment = methodResult.get(0);
        assertEquals(COMMENT_ID, comment.getId());
        assertEquals(POST_ID, comment.getPostId());
        assertEquals(AUTHOR, comment.getAuthor());
        assertEquals(CONTENT, comment.getCommentContent());
    }
}