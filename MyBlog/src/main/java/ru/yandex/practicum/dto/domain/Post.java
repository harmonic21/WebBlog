package ru.yandex.practicum.dto.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Post {

    private Long id;
    private String title;
    private String content;
    private String[] tags;
    private String image;
    private Long likesCount;
    private List<Comment> comments;

    public enum PostColumn {
        ID("id"),
        TITLE("title"),
        CONTENT("content"),
        TAGS("tags"),
        IMAGE("image"),
        LIKES_COUNT("likes_count");

        private final String columnName;

        PostColumn(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}
