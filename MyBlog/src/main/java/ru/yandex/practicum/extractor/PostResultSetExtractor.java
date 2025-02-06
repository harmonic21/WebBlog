package ru.yandex.practicum.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.domain.Post;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class PostResultSetExtractor implements ResultSetExtractor<List<Post>> {

    @Override
    public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Post> result = new ArrayList<>();
        while (rs.next()) {
            result.add(
                    new Post()
                            .setId(rs.getLong(Post.PostColumn.ID.getColumnName()))
                            .setTitle(rs.getString(Post.PostColumn.TITLE.getColumnName()))
                            .setContent(rs.getString(Post.PostColumn.CONTENT.getColumnName()))
                            .setTags(processTags(rs.getArray(Post.PostColumn.TAGS.getColumnName())))
                            .setImage(rs.getString(Post.PostColumn.IMAGE.getColumnName()))
                            .setLikesCount(rs.getLong(Post.PostColumn.LIKES_COUNT.getColumnName()))
            );
        }
        return result;
    }

    private String[] processTags(Array array) throws SQLException {
        if (Objects.nonNull(array)) {
            return Arrays.asList((Object[]) array.getArray()).stream()
                    .map(String.class::cast)
                    .toList().toArray(new String[0]);
        }
        return null;
    }
}
