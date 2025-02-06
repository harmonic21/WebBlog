package ru.yandex.practicum.repository;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.domain.Post;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PostRepository {

    private static final String SELECT_ALL = """
            SELECT * FROM POST""";

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
}
