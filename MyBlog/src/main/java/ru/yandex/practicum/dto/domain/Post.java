package ru.yandex.practicum.dto.domain;

import java.util.Objects;

public class Post {

    private Long id;
    private String title;
    private String content;
    private String[] tags;
    private String image;
    private Long likesCount;

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

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public String[] getTags() {
        return tags;
    }

    public Post setTags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Post setImage(String image) {
        this.image = image;
        return this;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public Post setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", likesCount=" + likesCount +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;

        return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(image, post.image) && Objects.equals(likesCount, post.likesCount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(content);
        result = 31 * result + Objects.hashCode(image);
        result = 31 * result + Objects.hashCode(likesCount);
        return result;
    }
}
