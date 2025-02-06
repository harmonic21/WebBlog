package ru.yandex.practicum.dto.domain;

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

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public Comment setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Comment setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Comment setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }
}
