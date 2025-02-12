package ru.yandex.practicum.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.yandex.practicum.dto.domain.Comment;
import ru.yandex.practicum.extractor.CommentResultSetExtractor;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private CommentResultSetExtractor resultSetExtractor;

    @Autowired
    private CommentRepository commentRepository;

    private static final String COMMENT_CONTENT_BASE = "test-content";
    private static final String COMMENT_CONTENT_NEW = "update-content";

    @Test
    @Order(1)
    public void shouldSaveComment() {
        namedParameterJdbcTemplate.update("INSERT INTO POST (title) VALUES (:title)", Map.of("title", "FOR TEST COMMENT"));
        assertTrue(namedParameterJdbcTemplate.query("SELECT * FROM COMMENT", resultSetExtractor).isEmpty());

        Comment comment = new Comment()
                .setPostId(1L)
                .setCommentContent("test-content")
                .setAuthor("comment-author");
        commentRepository.save(comment);

        var actualComments = namedParameterJdbcTemplate.query("SELECT * FROM COMMENT", resultSetExtractor);
        assertEquals(1, actualComments.size());
        assertEquals(COMMENT_CONTENT_BASE, actualComments.get(0).getCommentContent());
        assertEquals("comment-author", actualComments.get(0).getAuthor());
        assertEquals(1L, actualComments.get(0).getId());
    }

    @Test
    @Order(2)
    public void shouldFindAllCommentForPost() {
        var methodResult = commentRepository.findAllByPostId(1L);

        assertEquals(1, methodResult.size());
        assertEquals(COMMENT_CONTENT_BASE, methodResult.get(0).getCommentContent());
        assertEquals("comment-author", methodResult.get(0).getAuthor());
        assertEquals(1L, methodResult.get(0).getId());
    }

    @Test
    @Order(3)
    public void shouldUpdateCommentText() {
        commentRepository.updateCommentText(1L, COMMENT_CONTENT_NEW);

        var methodResult = commentRepository.findAllByPostId(1L);

        assertEquals(1, methodResult.size());
        assertEquals(COMMENT_CONTENT_NEW, methodResult.get(0).getCommentContent());
        assertEquals("comment-author", methodResult.get(0).getAuthor());
        assertEquals(1L, methodResult.get(0).getId());
    }

    @Test
    @Order(4)
    public void shouldDeleteAllCommentsByPostId() {
        commentRepository.deleteCommentsByPostId(1L);

        assertTrue(commentRepository.findAllByPostId(1L).isEmpty());
    }
}