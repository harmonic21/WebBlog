package ru.yandex.practicum.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Comment {
    private Long id;
    private Long postId;
    private String author;
    private String commentContent;

    public enum CommentColumn {
        ID("id"),
        POST_ID("post_id"),
        AUTHOR("author"),
        COMMENT_CONTENT("comment_content");

        private final String columnName;

        CommentColumn(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
