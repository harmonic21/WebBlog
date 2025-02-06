package ru.yandex.practicum.repository;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.domain.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class PostRepository {

    private static final String SELECT_ALL = """
            SELECT * FROM POST""";

    private static final String INSERT_STATEMENT = """
            INSERT INTO POST (title, content, tags, image, likes_count) VALUES (:title, :content, :tags, :image, :likes_count)""";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ResultSetExtractor<List<Post>> postResultSetExtractor;

    public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                          ResultSetExtractor<List<Post>> postResultSetExtractor) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.postResultSetExtractor = postResultSetExtractor;
    }

    public List<Post> findAll() {
        var posts = namedParameterJdbcTemplate.query(
                SELECT_ALL,
                postResultSetExtractor
        );
        return posts;
    }

    @Transactional
    public void save(Post post) {
        Map<String, Object> insertParams = new HashMap<>();
        insertParams.put(Post.PostColumn.TITLE.getColumnName(), post.getTitle());
        insertParams.put(Post.PostColumn.CONTENT.getColumnName(), post.getContent());
        insertParams.put(Post.PostColumn.TAGS.getColumnName(), post.getTags());
        insertParams.put(Post.PostColumn.IMAGE.getColumnName(), post.getImage());
        insertParams.put(Post.PostColumn.LIKES_COUNT.getColumnName(), post.getLikesCount());

        namedParameterJdbcTemplate.update(INSERT_STATEMENT, insertParams);
    }
}
