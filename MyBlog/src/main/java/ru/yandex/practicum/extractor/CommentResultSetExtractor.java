package ru.yandex.practicum.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.domain.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentResultSetExtractor implements ResultSetExtractor<List<Comment>> {

    @Override
    public List<Comment> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            comments.add(
                    new Comment()
                            .setId(rs.getLong(Comment.CommentColumn.ID.getColumnName()))
                            .setPostId(rs.getLong(Comment.CommentColumn.POST_ID.getColumnName()))
                            .setAuthor(rs.getString(Comment.CommentColumn.AUTHOR.getColumnName()))
                            .setCommentContent(rs.getString(Comment.CommentColumn.COMMENT_CONTENT.getColumnName()))
            );
        }
        return comments;
    }
}
