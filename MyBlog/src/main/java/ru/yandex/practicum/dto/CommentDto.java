package ru.yandex.practicum.dto;

public class CommentDto {
    private Long id;
    private PostDto post;
    private String author;
    private String commentContent;

    public Long getId() {
        return id;
    }

    public CommentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public PostDto getPost() {
        return post;
    }

    public CommentDto setPost(PostDto post) {
        this.post = post;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public CommentDto setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }
}
