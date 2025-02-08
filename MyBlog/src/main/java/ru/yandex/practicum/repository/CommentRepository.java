package ru.yandex.practicum.repository;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.domain.Comment;

import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class CommentRepository {

    private static final String SELECT_ALL_BY_POST_ID_STATEMENT = """
            SELECT * FROM COMMENT WHERE POST_ID = :post_id""";

    private static final String DELETE_BY_POST_ID_STATEMENT = """
            DELETE FROM COMMENT WHERE POST_ID = :post_id""";

    private static final String INSERT_STATEMENT = """
            INSERT INTO COMMENT (POST_ID, AUTHOR, COMMENT_CONTENT) VALUES (:post_id, :author, :comment_content)""";
    private static final String UPDATE_COMMENT_TEXT_STATEMENT = """
            UPDATE COMMENT SET COMMENT_CONTENT = :comment_content WHERE ID = :id
            """;

    private final ResultSetExtractor<List<Comment>> commentResultSetExtractor;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CommentRepository(ResultSetExtractor<List<Comment>> commentResultSetExtractor, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.commentResultSetExtractor = commentResultSetExtractor;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Comment> findAllByPostId(Long postId) {
        return namedParameterJdbcTemplate.query(
                SELECT_ALL_BY_POST_ID_STATEMENT,
                Map.of(Comment.CommentColumn.POST_ID.getColumnName(), postId),
                commentResultSetExtractor
        );
    }

    @Transactional
    public void deleteCommentsByPostId(Long postId) {
        namedParameterJdbcTemplate.update(DELETE_BY_POST_ID_STATEMENT, Map.of(Comment.CommentColumn.POST_ID.getColumnName(), postId));
    }

    @Transactional
    public void save(Comment comment) {
        Map<String, Object> insertParams = Map.of(
                Comment.CommentColumn.POST_ID.getColumnName(), comment.getPostId(),
                Comment.CommentColumn.AUTHOR.getColumnName(), comment.getAuthor(),
                Comment.CommentColumn.COMMENT_CONTENT.getColumnName(), comment.getCommentContent()
        );
        namedParameterJdbcTemplate.update(INSERT_STATEMENT, insertParams);
    }

    @Transactional
    public void updateCommentText(Long id, String newText) {
        namedParameterJdbcTemplate.update(UPDATE_COMMENT_TEXT_STATEMENT, Map.of(
                Comment.CommentColumn.ID.getColumnName(), id,
                Comment.CommentColumn.COMMENT_CONTENT.getColumnName(), newText
        ));
    }
}
